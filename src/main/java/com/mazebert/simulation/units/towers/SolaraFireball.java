package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SplashAbility;

public strictfp class SolaraFireball extends SplashAbility {
    public SolaraFireball() {
        super(2, 0.4f);
    }

    @Override
    public String getTitle() {
        return "Fireball";
    }

    @Override
    public String getIconFile() {
        return "0067_fireball_512";
    }
}
