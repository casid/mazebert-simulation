package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchClawAbility extends StackableAbility<Tower> {

    public static final float damageBonus = 1.11f;
    public static final float speedBonus = -0.33f;

    private float damage;
    private float speed;

    @Override
    protected void updateStacks() {
        getUnit().addAddedRelativeBaseDamage(-damage);
        getUnit().addAttackSpeed(-speed);

        float multiplier = getMultiplier();
        damage = multiplier * damageBonus;
        speed = multiplier * speedBonus;

        getUnit().addAddedRelativeBaseDamage(damage);
        getUnit().addAttackSpeed(speed);
    }

    private float getMultiplier() {
        return getStackCount() * getUnit().getEldritchCardModifier();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Cursed Strength";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(damageBonus) + " damage.\n" +
                format.percentWithSignAndUnit(speedBonus) + " attack speed.";
    }
}
