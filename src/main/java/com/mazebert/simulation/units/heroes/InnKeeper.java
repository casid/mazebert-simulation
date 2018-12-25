package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class InnKeeper extends Hero {

    public InnKeeper() {
        addAbility(new InnKeeperAbility());
        addAbility(new ShadowManeAbility());
    }

    @Override
    public String getName() {
        return "Innkeeper";
    }

    @Override
    public String getDescription() {
        return "Welcome traveller!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "innkeeper_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Vigi";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isSupporterReward() {
        return true;
    }
}
