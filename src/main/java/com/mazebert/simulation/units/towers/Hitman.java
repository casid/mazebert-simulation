package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.ColorChangeAbility;

public class Hitman extends Tower {
    public Hitman() {
        setCooldown(5.0f);

        addAbility(new ColorChangeAbility());
    }
}
