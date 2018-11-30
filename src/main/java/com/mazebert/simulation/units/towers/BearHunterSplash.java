package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SplashAbility;

public class BearHunterSplash extends SplashAbility {
    public BearHunterSplash() {
        super(2, 0.4f);
    }

    @Override
    public String getTitle() {
        return "Blood splatter";
    }

    @Override
    public String getDescription() {
        return "Creeps in " + getRange() + " range around the trapped creep receive " + format.percent(getDamageFactor()) + "% damage.";
    }

    @Override
    public String getIconFile() {
        return "0022_bloodyweapon_512";
    }
}
