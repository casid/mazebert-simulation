package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Tears extends Potion {
    public Tears() {
        super(new TearsAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getIcon() {
        return "9006_TearsPotion";
    }

    @Override
    public String getName() {
        return "Tears of the Gods";
    }

    @Override
    public String getDescription() {
        return "There is no stronger force in the universe.\nUse them wisely.";
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
        return "geX";
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }
}
