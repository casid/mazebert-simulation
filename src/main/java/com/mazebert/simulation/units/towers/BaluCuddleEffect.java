package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.CooldownAbility;

public strictfp class BaluCuddleEffect extends CooldownAbility<Tower> {

    @Override
    protected float getCooldown() {
        return BaluCuddle.SLOW_DOWN_TIME;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        getUnit().addAttackSpeed(-BaluCuddle.SLOW_DOWN);
    }

    @Override
    public void dispose(Tower unit) {
        getUnit().addAttackSpeed(+BaluCuddle.SLOW_DOWN);
        super.dispose(unit);
    }

    @Override
    protected boolean onCooldownReached() {
        getUnit().removeAbility(this);
        return true;
    }
}
