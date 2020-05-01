package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class EssenceOfLuck extends Potion {
    public EssenceOfLuck() {
        super(new EssenceOfLuckAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getIcon() {
        return "9010_EssenceOfLuckPotion_512";
    }

    @Override
    public String getName() {
        return "Essence of Luck";
    }

    @Override
    public String getDescription() {
        return "Distilled by a master alchemist, this potion will turn a tower's luck around.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getAuthor() {
        return "jhoijhoi";
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }
}
