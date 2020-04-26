package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class RabbitMultishot extends Ability<Tower> implements OnLevelChangedListener {

    private int additionalTargets;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onLevelChanged.add(this);

        setAdditionalTargets(1);
    }

    @Override
    public void dispose(Tower unit) {
        setAdditionalTargets(0);

        unit.onLevelChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        int additionalTargets = 1;
        if (newLevel >= 16) {
            additionalTargets = 2;
        }
        setAdditionalTargets(additionalTargets);
    }

    private void setAdditionalTargets(int additionalTargets) {
        if (this.additionalTargets != additionalTargets) {
            addTargets(-this.additionalTargets);
            this.additionalTargets = additionalTargets;
            addTargets(+this.additionalTargets);
        }
    }

    private void addTargets(int targets) {
        AttackAbility attackAbility = getUnit().getAbility(AttackAbility.class);
        if (attackAbility != null) {
            attackAbility.setTargets(attackAbility.getTargets() + targets);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Carrots for Everyone";
    }

    @Override
    public String getDescription() {
        return "Baby Rabbit attacks up to 2 targets at once.";
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
