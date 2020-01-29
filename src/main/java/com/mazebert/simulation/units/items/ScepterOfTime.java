package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;

public strictfp class ScepterOfTime extends Item {

    public ScepterOfTime() {
        super(new ScepterOfTimeAbility());
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
        if (Sim.context().version >= Sim.v20) {
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
