package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class InnKeeperAbility extends HeroTowerBuffAbility {

    public InnKeeperAbility() {
        super(true);
    }

    @Override
    protected void buffTower(Tower tower) {
        tower.addAbility(new InnKeeperEffect());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Drink with Me!";
    }

    @Override
    public String getDescription() {
        return "After drinking a potion,\nyour towers will burp\nfrom time to time.";
    }
}
