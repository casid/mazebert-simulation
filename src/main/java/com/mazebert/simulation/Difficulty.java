package com.mazebert.simulation;

public strictfp enum Difficulty {
    Normal(1, 1.25, 0.0003, 0.75),
    Nightmare(2, 1.5, 0.0006, 1.0),
    Hell(3, 1.75, 0.0009, 1.25);

    private static final Difficulty[] LOOKUP;

    static {
        int maxId = 0;
        for (Difficulty difficulty : Difficulty.values()) {
            maxId = StrictMath.max(maxId, difficulty.id);
        }
        LOOKUP = new Difficulty[maxId + 1];
        for (Difficulty difficulty : Difficulty.values()) {
            LOOKUP[difficulty.id] = difficulty;
        }
    }

    public static Difficulty forId(int id) {
        if (id <= 0 || id >= LOOKUP.length) {
            return null;
        }
        return LOOKUP[id];
    }

    Difficulty(int id, double earlyGameFactor, double midGameFactor, double experienceModifier) {
        this.id = id;
        this.earlyGameFactor = earlyGameFactor;
        this.midGameFactor = midGameFactor;
        this.experienceModifier = experienceModifier;
    }

    @SuppressWarnings("unused") // By client
    public double getExperienceModifierForDisplay() {
        return experienceModifier + 0.25;
    }

    public final int id;
    public final double earlyGameFactor;
    public final double midGameFactor;
    public final double endGameFactor = 0.078; // We want the end game factor to be always the same for EVERY difficulty!
    public final double experienceModifier;
}
