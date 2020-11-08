package com.mazebert.simulation.changelog;

import com.mazebert.simulation.Sim;

public strictfp class ChangelogEntry {
    private static final String[] INITIAL_CHANGES = {"Added to the game"};

    public static final ChangelogEntry DAWN_OF_LIGHT = new ChangelogEntry(Sim.vDoL, true, 2019);
    public static final ChangelogEntry RISE_OF_CTHULHU = new ChangelogEntry(Sim.vRoC, true, 2020);

    private final int simVersion;
    private final boolean season;
    private final int year;
    private final String[] changes;

    public ChangelogEntry(int simVersion, boolean season, int year, String ... changes) {
        this.simVersion = simVersion;
        this.season = season;
        this.year = year;
        if (changes.length == 0) {
            changes = INITIAL_CHANGES;
        }
        this.changes = changes;
    }

    public int getSimVersion() {
        return simVersion;
    }

    public boolean isSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public String[] getChanges() {
        return changes;
    }

    public boolean isInitial() {
        return changes == INITIAL_CHANGES;
    }
}
