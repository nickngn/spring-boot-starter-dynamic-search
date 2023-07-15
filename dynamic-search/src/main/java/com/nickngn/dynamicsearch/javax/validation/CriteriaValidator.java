/*
 * MIT License
 *
 * Copyright (c) [2023] [NickNgn]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.nickngn.dynamicsearch.javax.validation;

import com.nickngn.dynamicsearch.javax.SearchTemplate;
import com.nickngn.dynamicsearch.javax.Criteria;

import javax.validation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class CriteriaValidator implements ConstraintValidator<ValidatedCriteria, SearchTemplate> {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public boolean isValid(SearchTemplate template, ConstraintValidatorContext context) {
        List<String> errMsgs = new ArrayList<>();

        errMsgs.addAll(customValidate(template));
        errMsgs.addAll(validateSyntax(template));

        if (!errMsgs.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.join("; ", errMsgs))
                    .addConstraintViolation();
        }
        return errMsgs.isEmpty();
    }

    private static List<String> customValidate(SearchTemplate template) {
        SearchTemplate.ConditionList conditionList = template.customValidate(new SearchTemplate.ConditionList());
        if (conditionList == null) return Collections.emptyList();

        List<String> errMsgs = new ArrayList<>();
        conditionList.getConditions().forEach(condition -> {
            if (condition.getCondition().get()) {
                errMsgs.add(condition.getErrorMessage());
            }
        });
        return errMsgs;
    }


    public List<String> validateSyntax(SearchTemplate template) {
        Class<?> refClass = template.getReferenceClass();
        if (refClass == null) return Collections.emptyList();

        List<String> fields = Stream.of(refClass.getDeclaredFields()).map(Field::getName).toList();
        List<String> errMsgs = new ArrayList<>();
        for (Criteria criteria: template.getCriteria()) {
            if (!fields.contains(criteria.key())) {
                errMsgs.add(String.format("Field '%s' isn't allowed searchable key", criteria.key()));
                continue;
            }
            Set<? extends ConstraintViolation<?>> violations = validator.validateValue(refClass, criteria.key(), criteria);
            if (!violations.isEmpty()) {
                List<String> msgs = violations.stream().map(ConstraintViolation::getMessage).toList();
                errMsgs.addAll(msgs);
            }
        }

        return errMsgs;
    }
}
