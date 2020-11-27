package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class BloodDemonBladeAbility extends Ability<Tower> {

    private float damage;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addAddedAbsoluteBaseDamage(damage);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addAddedAbsoluteBaseDamage(-damage);
        super.dispose(unit);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Damnation!";
    }

    @Override
    public String getLevelBonus() {
        return "+" + (int) damage + " base damage.";
    }
}
