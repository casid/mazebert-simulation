package com.mazebert.simulation.util;

import com.mazebert.java8.Consumer;
import com.mazebert.java8.Predicate;
import com.mazebert.simulation.plugins.random.RandomPlugin;

import java.util.Arrays;

public strictfp final class SafeIterationArray<T> {
    private static final int LOAD_FACTOR = 2;

    private Object[] elements;
    private int size;
    private int iterationDepth;

    public SafeIterationArray() {
        this(10);
    }

    public SafeIterationArray(int capacity) {
        elements = new Object[capacity];
    }

    public void add(T element) {
        if (size + 1 > elements.length) {
            elements = Arrays.copyOf(elements, size * LOAD_FACTOR);
        }
        elements[size++] = element;
    }

    public int size() {
        return size;
    }

    public boolean remove(T element) {
        for (int i = 0; i < size; ++i) {
            if (element == elements[i]) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public void remove(int index) {
        if (iterationDepth > 0) {
            elements[index] = null;
        } else {
            int numMoved = size - index - 1;
            if (numMoved > 0) {
                System.arraycopy(elements, index + 1, elements, index, numMoved);
            }
            elements[--size] = null;
        }
    }

    @SuppressWarnings("unchecked")
    public void forEach(Consumer<T> consumer) {
        pushIteration();
        try {
            for (int i = 0; i < size; ++i) {
                if (elements[i] != null) {
                    consumer.accept((T) elements[i]);
                }
            }
        } finally {
            popIteration();
        }
    }

    @SuppressWarnings("unchecked")
    public void forEachIndexed(IndexConsumer<T> consumer) {
        pushIteration();
        try {
            for (int i = 0; i < size; ++i) {
                if (elements[i] != null) {
                    consumer.accept(i, (T) elements[i]);
                }
            }
        } finally {
            popIteration();
        }
    }

    @SuppressWarnings("unchecked")
    public T find(Predicate<T> predicate) {
        pushIteration();
        try {
            for (int i = 0; i < size; ++i) {
                if (elements[i] != null && predicate.test((T) elements[i]) ) {
                    return (T) elements[i];
                }
            }
        } finally {
            popIteration();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public T findRandom(Predicate<T> predicate, RandomPlugin randomPlugin) {
        pushIteration();
        try {
            int count = 0;
            for (int i = 0; i < size; ++i) {
                if (elements[i] != null && predicate.test((T) elements[i]) ) {
                    ++count;
                }
            }

            if (count == 0) {
                return null;
            }

            int element = randomPlugin.getInt(0, count - 1);
            count = 0;

            for (int i = 0; i < size; ++i) {
                if (elements[i] != null && predicate.test((T) elements[i]) ) {
                    if (count++ == element) {
                        return (T) elements[i];
                    }
                }
            }
        } finally {
            popIteration();
        }

        return null;
    }

    private void pushIteration() {
        ++iterationDepth;
    }

    private void popIteration() {
        --iterationDepth;

        if (iterationDepth == 0) {
            removeElementsAfterIteration();
        }
    }

    private void removeElementsAfterIteration() {
        for (int i = 0; i < size; ++i) {
            if (elements[i] == null) {
                remove(i--);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (iterationDepth > 0) {
            throw new IllegalStateException("Unsafe operation: calling get during iteration!");
        }
        return (T) elements[index];
    }

    public interface IndexConsumer<T> {
        void accept(int index, T element);
    }
}
