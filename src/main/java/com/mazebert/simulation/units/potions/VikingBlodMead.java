package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class VikingBlodMead extends Potion {
    public VikingBlodMead() {
        super(new VikingBlodMeadAbility(), new VikingBlodMeadRiggedCardAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, false, 2021)
        );
    }

    @Override
    public String getIcon() {
        return "viking_blod_mead";
    }

    @Override
    public String getName() {
        return "Viking Blod Mead";
    }

    @Override
    public String getDescription() {
        return "Don't spill the mead,\nspill the blood of your enemies.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "2.3";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isSupporterReward() {
        return true;
    }

    @Override
    public boolean isTradingAllowed() {
        return false;
    }

    @Override
    public boolean isProphecy() {
        return true;
    }
}
