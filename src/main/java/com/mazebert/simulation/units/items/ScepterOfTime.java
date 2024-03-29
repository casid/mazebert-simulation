package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class ScepterOfTime extends Item {

    public ScepterOfTime() {
        super(Sim.context().version >= Sim.vDoLEnd ? new ScepterOfTimeAbility() : new ScepterOfTimeLegacyAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v24, false, 2021, "Scepter of Time can make time pass 5x faster."),
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Scepter of Time now has an active ability that is shared by all players."),
                new ChangelogEntry(Sim.v13, false, 2019, "Scepter of Time can no longer be transmuted."),
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Scepter of Time";
    }

    @Override
    public String getDescription() {
        return "This ancient scepter is so old, years pass with the blink of an eye.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "0000_poisondagger_512";
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public boolean isTradingAllowed() {
        if (Sim.context().version < Sim.v13) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isUniqueInstance() {
        if (Sim.context().version >= Sim.vDoLEnd) {
            return true;
        }
        return false;
    }

    @Override
    public Rarity getDropRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getAuthor() {
        return "SchlawinerUSA";
    }
}
