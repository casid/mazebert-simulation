package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SplashAbility;

public strictfp class BlueWhaleSplash extends SplashAbility {
    public BlueWhaleSplash() {
        super(1, 0.2f);
    }

    @Override
    public String getTitle() {
        return "Splash";
    }

    @Override
    public String getDescription() {
        return "Creeps within " + getRange() + " range of Blue Whale's target receive " + format.percent(getDamageFactor()) + "% splash damage.";
    }
}
