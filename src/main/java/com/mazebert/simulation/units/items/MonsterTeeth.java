package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class MonsterTeeth extends Item {

    public MonsterTeeth() {
        super(new MonsterTeethAbility());
    }

    @Override
    public String getIcon() {
        return "0015_bite_512";
    }

    @Override
    public String getName() {
        return "Monster Teeth";
    }

    @Override
    public String getDescription() {
        return "Those teeth haven't seen a dentist in their entire life.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
