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
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.nickngn.dynamicsearch.springboottest.searchexample;

import io.github.nickngn.dynamicsearch.jakarta.builder.SpecBuilder;
import io.github.nickngn.dynamicsearch.jakarta.builder.SpecBuilders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final EntityRepository entityRepository;
    private final SpecBuilder<SearchEntity> builder = SpecBuilders.getInstance(SearchEntity.class);

    public Page<SearchEntity> search(SearchCriteria searchCriteria) {
        Specification<SearchEntity> spec = builder.build(searchCriteria.getCriteria());
        return entityRepository.findAll(spec, searchCriteria.getPageable());
    }
}
