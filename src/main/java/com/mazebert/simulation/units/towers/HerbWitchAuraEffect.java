package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class HerbWitchAuraEffect extends Ability<Tower> implements OnLevelChangedListener {

    private float bonus;

    public HerbWitchAuraEffect(Tower origin) {
        setOrigin(origin);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        getOriginTower().onLevelChanged.add(this);
        addBonus();
    }

    @Override
    protected void dispose(Tower unit) {
        removeBonus();
        getOriginTower().onLevelChanged.remove(this);
        super.dispose(unit);
    }

    private Tower getOriginTower() {
        return (Tower)getOrigin();
    }

    @Override
    public void onLevelChanged(Tower tower, int oldLevel, int newLevel) {
        removeBonus();
        addBonus();
    }

    private void addBonus() {
        if (bonus == 0) {
            bonus = 0.1f + (getOriginTower().getLevel() * 0.005f);
            getUnit().addAttackSpeed(bonus);
        }
    }

    private void removeBonus() {
        if (bonus > 0) {
            getUnit().addAttackSpeed(-bonus);
            bonus = 0;
        }
    }
}
