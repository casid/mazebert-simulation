package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class UselessMachine extends Item {

    public UselessMachine() {
        super(new UselessMachineAbility());
    }

    @Override
    public String getName() {
        return "The useless machine";
    }

    @Override
    public String getDescription() {
        return "Somebody forgot to change the battery and it won't flip the switch back on.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getIcon() {
        return "useleass_machine";
    }

    @Override
    public int getItemLevel() {
        return 160;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Kyrandia";
    }
}
