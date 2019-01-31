package com.mazebert.simulation;

public strictfp enum WaveType {
    Normal(10),
    Mass(20),
    Boss(1),
    Air(5),
    Challenge(1),
    MassChallenge(20),
    Horseman(1);

    public final int creepCount;

    WaveType(int creepCount) {
        this.creepCount = creepCount;
    }

    public float getMinSecondsToNextCreep() {
        switch (this) {
            case Mass:
            case MassChallenge:
                return 0.2f;
            case Air:
                return 1.6f;
        }
        return 1.0f;
    }

    public float getMaxSecondsToNextCreep() {
        switch (this) {
            case Mass:
            case MassChallenge:
                return 0.6f;
            case Air:
                return 3.2f;
        }
        return 1.6f;
    }

    public double getHealthMultiplier() {
        switch (this) {
            case Normal:
                return 1.0;
            case Air:
                return 0.60;
            case Mass:
                return 0.62;
            case Boss:
                return 0.55;
            case Horseman:
                return 1.0;
            case Challenge:
                return 2.0;
            case MassChallenge:
                return 2.0;
        }
        return 1.0;
    }

    public boolean isBoss() {
        return this == Boss || this == Challenge || this == Horseman;
    }
}
