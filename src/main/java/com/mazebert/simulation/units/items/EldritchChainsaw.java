package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemUniqueAbility;

public strictfp class EldritchChainsaw extends Item {
    public EldritchChainsaw() {
        setAbilities(new EldritchChainsawAbility(this), new EldritchItemUniqueAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021, "Damage per level reduced from 4% to 0.6%"),
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Eldritch Chainsaw";
    }

    @Override
    public String getDescription() {
        return "You're goin' down!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "2.2";
    }

    @Override
    public String getIcon() {
        return "eldritch_chainsaw";
    }

    @Override
    public int getItemLevel() {
        return 67;
    }

    @Override
    public boolean isEldritch() {
        return true;
    }

    @Override
    public boolean isDropable() {
        return false;
    }
}
