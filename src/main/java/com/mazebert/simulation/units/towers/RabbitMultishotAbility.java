package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class RabbitMultishotAbility extends AttackAbility implements OnLevelChangedListener {

    public RabbitMultishotAbility() {
        super(2);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onLevelChanged.add(this);
    }

    @Override
    public void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onLevelChanged(Tower tower, int oldLevel, int newLevel) {
        int targets = 2;
        if (newLevel >= 16) {
            targets = 3;
        }
        setTargets(targets);
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
