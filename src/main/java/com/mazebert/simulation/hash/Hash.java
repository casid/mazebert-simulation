package com.mazebert.simulation.hash;

import java.util.UUID;

public strictfp class Hash {
    private int result;

    public void add(boolean value) {
        result = 31 * result + (value ? 1 : 0);
    }

    public void add(int value) {
        result = 31 * result + value;
    }

    public void add(long value) {
        result = 31 * result + (int) (value ^ (value >>> 32));
    }

    public void add(float value) {
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
    }

    public void add(double value) {
        add(Double.doubleToLongBits(value));
    }

    public void add(Enum value) {
        if (value == null) {
            add(0);
        } else {
            add(value.ordinal() + 1);
        }
    }

    public void add(Hashable value) {
        if (value == null) {
            add(0);
        } else {
            value.hash(this);
        }
    }

    public void add(UUID value) {
        if (value == null) {
            add(0);
        } else {
            add(value.getMostSignificantBits());
            add(value.getLeastSignificantBits());
        }
    }

    public void reset() {
        result = 0;
    }

    public int get() {
        return result;
    }
}
