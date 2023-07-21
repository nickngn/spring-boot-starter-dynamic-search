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

package io.github.nickngn.dynamicsearch.javax.builder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Fly-weight holder for {@link SpecBuilder} to reuse builder per Entity types
 */
public final class SpecBuilders {

    private static final ConcurrentHashMap<Class<?>, SpecBuilder<?>> BUILDER_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> SpecBuilder<T> getInstance(Class<T> klass) {
        if (BUILDER_MAP.containsKey(klass)) {
            return (SpecBuilder<T>) BUILDER_MAP.get(klass);
        }
        SpecBuilder<T> specBuilder = new SpecBuilder<>();
        BUILDER_MAP.put(klass, specBuilder);
        return specBuilder;
    }

}
