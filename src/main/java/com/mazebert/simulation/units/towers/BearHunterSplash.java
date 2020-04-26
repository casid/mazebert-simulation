package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SplashAbility;

public strictfp class BearHunterSplash extends SplashAbility {
    public BearHunterSplash() {
        super(2, 0.4f);
    }

    @Override
    public String getTitle() {
        return "Blood Spatter";
    }

    @Override
    public String getDescription() {
        return "Creeps in " + getRange() + " range around trapped creeps receive " + format.percent(getDamageFactor()) + "% damage.";
    }

    @Override
    public String getIconFile() {
        return "0022_bloodyweapon_512";
    }
}
