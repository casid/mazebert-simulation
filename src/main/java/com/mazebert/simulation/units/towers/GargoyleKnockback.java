package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.KnockbackAbility;

public strictfp class GargoyleKnockback extends KnockbackAbility implements OnLevelChangedListener {
    public GargoyleKnockback() {
        setChance(0.07f);
        setChancePerLevel(0.0007f);
        setDistance(1.0f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onLevelChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        if (newLevel >= 16) {
            setDistance(2);
        } else {
            setDistance(1);
        }
    }

    @Override
    public String getTitle() {
        return "Divine Defense";
    }

    @Override
    public String getDescription() {
        return "Hurtles from his perch to slam into the creep wave. " + super.getDescription();
    }

    @Override
    public String getLevelBonus() {
        return "+1 tile at level 16";
    }

    @Override
    public String getIconFile() {
        return "stone1_512";
    }
}
