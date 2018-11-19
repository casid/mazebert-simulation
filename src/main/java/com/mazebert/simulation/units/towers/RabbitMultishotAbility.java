package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public class RabbitMultishotAbility extends AttackAbility {

    public RabbitMultishotAbility() {
        super(2);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        // TODO
    }

    @Override
    public void dispose(Tower unit) {
        // TODO
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Multishot";
    }

    @Override
    public String getDescription() {
        return "Rabbit shoots with bad ass carrots and can attack up to 2 creeps at once.";
    }

    @Override
    public String getIconFile() {
        return "0056_throw_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 1 target at level 16";
    }
}
