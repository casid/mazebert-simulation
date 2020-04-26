package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SplashAbility;

public strictfp class DandelionSplash extends SplashAbility {
    public DandelionSplash() {
        super(1, 0.33f);
    }

    @Override
    public String getTitle() {
        return "Seedsplash";
    }

    @Override
    public String getDescription() {
        return "Creeps within " + getRange() + " range of Dandelion's target receive " + format.percent(getDamageFactor()) + "% splash damage.";
    }
}
