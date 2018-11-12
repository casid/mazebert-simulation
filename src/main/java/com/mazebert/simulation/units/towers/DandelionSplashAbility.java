package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SplashDamageAbility;

public class DandelionSplashAbility extends SplashDamageAbility {
    public DandelionSplashAbility() {
        setRange(1);
        setDamageFactor(0.33f);
    }
}