package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class EssenceOfWisdom extends Potion {
    public EssenceOfWisdom() {
        super(new EssenceOfWisdomAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getIcon() {
        return "9009_WisdomPotion";
    }

    @Override
    public String getName() {
        return "Essence of Wisdom";
    }

    @Override
    public String getDescription() {
        return "The tears of a famous elephant deity are captured in this flask.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 120;
    }

    @Override
    public String getAuthor() {
        return "Thomadin";
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }
}
