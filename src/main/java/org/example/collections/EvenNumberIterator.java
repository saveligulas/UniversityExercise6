package org.example.collections;

import java.util.Collection;
import java.util.Iterator;

public class EvenNumberIterator implements Iterator {
    private Iterator<Number> numbers;
    private Iterator<Number> searcher;
    private int nextNumberCount;

    public EvenNumberIterator(Collection<Number> numbers) {
        this.numbers = numbers.iterator();
        this.searcher = numbers.iterator();
    }

    private boolean hasNextEvenNumber() {
        nextNumberCount = 0;
        while (searcher.hasNext()) {
            if (searcher.next().intValue() % 2 == 0) {
                return true;
            }
            nextNumberCount++;
        }
        return false;
    }

    @Override
    public boolean hasNext() {
        return hasNextEvenNumber();
    }

    @Override
    public Object next() {
        for (int i = 0; i < nextNumberCount; i++) {
            numbers.next();
        }
        return numbers.next();
    }
}
