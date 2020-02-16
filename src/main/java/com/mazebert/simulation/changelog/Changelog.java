package com.mazebert.simulation.changelog;

public strictfp class Changelog {
    private final ChangelogEntry[] entries;

    public Changelog(ChangelogEntry ... entries) {
        this.entries = entries;
    }

    public ChangelogEntry[] getEntries() {
        return entries;
    }
}
