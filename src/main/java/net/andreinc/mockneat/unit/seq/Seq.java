package net.andreinc.mockneat.unit.seq;

/**
 * Copyright 2017, Andrei N. Ciobanu

 Permission is hereby granted, free of charge, to any user obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, FREE_TEXT OF OR PARAM CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS PARAM THE SOFTWARE.
 */

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.interfaces.MockUnit;

import java.util.Iterator;
import java.util.function.Supplier;

import static net.andreinc.mockneat.utils.ValidationUtils.IMPOSSIBLE_TO_SEQ_OVER_EMPTY_COLLECTION;
import static net.andreinc.mockneat.utils.ValidationUtils.isTrue;
import static net.andreinc.mockneat.utils.ValidationUtils.notNull;


// TODO - documentation
public class  Seq<T> implements MockUnit<T> {

    private final MockNeat mockNeat;

    private final Iterable<T> iterable;
    private Iterator<T> iterator;

    private boolean cycle = false;
    private T after = null;

    public Seq(MockNeat mockNeat, Iterable<T> iterable) {

        notNull(mockNeat, "mockNeat");
        notNull(iterable, "iterable");

        this.mockNeat = mockNeat;
        this.iterable = iterable;
        this.iterator = iterable.iterator();

        isTrue(iterator.hasNext(), IMPOSSIBLE_TO_SEQ_OVER_EMPTY_COLLECTION);
    }

    public Seq<T> cycle(boolean value) {
        this.cycle = value;
        return this;
    }

    public Seq<T> after(T after) {
        this.after = after;
        return this;
    }

    @Override
    public Supplier<T> supplier() {
        return () -> {
            if (iterator.hasNext())
                return (T) iterator.next();
            else
                if (cycle) {
                    this.iterator = iterable.iterator();
                    return (T) iterator.next();
                }
                else
                    return(T) after;
        };
    }
}