package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnRangeChangedListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchMarshRifleAbility extends StackableAbility<Tower> implements OnRangeChangedListener {

    public static final float damageBonus = 0.3f;
    public static final float attackSpeedBonus = -0.4f;

    private float damage;
    private float attackSpeed;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onRangeChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onRangeChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        getUnit().addAddedRelativeBaseDamage(-damage);
        getUnit().addAttackSpeed(-attackSpeed);

        float multiplier = getMultiplier();
        damage = multiplier * damageBonus * getWaterTiles();
        attackSpeed = multiplier * attackSpeedBonus;

        getUnit().addAddedRelativeBaseDamage(damage);
        getUnit().addAttackSpeed(attackSpeed);
    }

    private int getWaterTiles() {
        return Sim.context().gameGateway.getMap().countWaterTiles(getUnit().getX(), getUnit().getY(), getUnit().getRange());
    }

    @Override
    public void onRangeChanged(Tower tower) {
        updateStacks();
    }

    private float getMultiplier() {
        return getStackCount() * getUnit().getEldritchCardModifier();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(damageBonus) + " damage per water tile in range.\n" +
                format.percentWithSignAndUnit(attackSpeedBonus) + " attack speed.";
    }
}
