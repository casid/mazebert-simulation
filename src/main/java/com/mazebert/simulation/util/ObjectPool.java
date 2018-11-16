package com.mazebert.simulation.util;

import java.lang.reflect.Array;
import java.util.Arrays;

public final strictfp class ObjectPool<T> {
    private static final int LOAD_FACTOR = 2;

    private final ObjectPoolFactory<T> factory;

    private T[] active;
    private int activeSize;
    private T[] inactive;
    private int inactiveSize;


    @SuppressWarnings("unchecked")
    public ObjectPool(ObjectPoolFactory<T> factory, Class<? extends T> elementClass, int capacity) {
        this.factory = factory;
        this.active = (T[]) Array.newInstance(elementClass, capacity);
        this.inactive = (T[]) Array.newInstance(elementClass, capacity);
    }

    public T[] getActive() {
        return active;
    }

    public T add() {
        T element = borrowInactive();
        if (element == null) {
            element = factory.create();
        }
        addActive(element);
        return element;
    }

    public void recycle(T element) {
        removeActive(element);
        addInactive(element);
    }

    private T borrowInactive() {
        if (inactiveSize > 0) {
            T element = inactive[--inactiveSize];
            inactive[inactiveSize] = null;
            return element;
        }
        return null;
    }

    private void removeActive(T element) {
        int index = getActiveIndex(element);
        if (index < 0) {
            return;
        }

        if (index < --activeSize) {
            active[index] = active[activeSize];
            active[activeSize] = null;
        } else {
            active[index] = null;
        }
    }

    private int getActiveIndex(T element) {
        for (int i = 0; i < activeSize; ++i) {
            if (active[i] == element) {
                return i;
            }
        }
        return -1;
    }

    private void addActive(T element) {
        if (activeSize + 1 > active.length) {
            active = Arrays.copyOf(active, activeSize * LOAD_FACTOR);
        }
        active[activeSize++] = element;
    }

    private void addInactive(T element) {
        if (inactiveSize + 1 > inactive.length) {
            inactive = Arrays.copyOf(inactive, inactiveSize * LOAD_FACTOR);
        }
        inactive[inactiveSize++] = element;
    }

    public int getActiveSize() {
        return activeSize;
    }
}
