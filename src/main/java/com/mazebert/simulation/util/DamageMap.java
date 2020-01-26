package com.mazebert.simulation.util;

import com.mazebert.simulation.units.towers.Tower;

import java.util.Arrays;

public final strictfp class DamageMap {
    private static final int LOAD_FACTOR = 2;

    private Tower[] keys;
    private double[] values;
    private int size;

    public DamageMap() {
        this(16);
    }

    public DamageMap(int capacity) {
        keys = new Tower[capacity];
        values = new double[capacity];
    }

    public void add(Tower key, double value) {
        int i = indexOf(key);
        if (i == -1) {
            if (size + 1 > keys.length) {
                keys = Arrays.copyOf(keys, size * LOAD_FACTOR);
                values = Arrays.copyOf(values, size * LOAD_FACTOR);
            }
            keys[size] = key;
            values[size] = value;
            ++size;
        } else {
            values[i] += value;
        }
    }

    public double get(Tower key) {
        int i = indexOf(key);
        if (i == -1) {
            return 0;
        }
        return values[i];
    }

    public void forEachNormalized(DamageConsumer damageConsumer) {
        double total = 0;
        for (int i = 0; i < size; ++i) {
            total += values[i];
        }

        if (total == 0) {
            return;
        }

        for (int i = 0; i < size; ++i) {
            damageConsumer.accept(keys[i], values[i] / total);
        }
    }

    private int indexOf(Tower key) {
        for (int i = 0; i < size; ++i) {
            if (keys[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public interface DamageConsumer {
        void accept(Tower tower, double damage);
    }
}
