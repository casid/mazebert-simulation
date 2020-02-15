package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Nillos extends Potion {
    public Nillos() {
        super(new NillosAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getIcon() {
        return "9007_NillosPotion";
    }

    @Override
    public String getName() {
        return "Nillos' Elixir of Cunning";
    }

    @Override
    public String getDescription() {
        return "How do you know what it's like to be stupid if you've never been smart?";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public String getAuthor() {
        return "Alex Nill";
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }
}
