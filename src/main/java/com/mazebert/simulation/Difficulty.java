package com.mazebert.simulation;

public strictfp enum Difficulty {
    Easy(1.25, 0.0003, 0.75),
    Normal(1.5, 0.0006, 1.0),
    Hard(1.75, 0.0009, 1.25);

    Difficulty(double earlyGameFactor, double midGameFactor, double experienceModifier) {
        this.earlyGameFactor = earlyGameFactor;
        this.midGameFactor = midGameFactor;
        this.experienceModifier = experienceModifier;
    }

    public final double earlyGameFactor;
    public final double midGameFactor;
    public final double endGameFactor = 0.078; // We want the end game factor to be always the same for EVERY difficulty!
    public final double experienceModifier;
}
