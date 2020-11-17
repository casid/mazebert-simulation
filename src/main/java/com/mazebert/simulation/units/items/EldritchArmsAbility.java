package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchArmsAbility extends Ability<Tower> {

    public static final int targets = 8;
    public static final float attackSpeed = -8.0f;
    public static final float luck = -1.0f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        AttackAbility attackAbility = unit.getAbility(AttackAbility.class);
        if (attackAbility != null) {
            attackAbility.setTargets(attackAbility.getTargets() + targets);
        }
        unit.addAttackSpeed(attackSpeed);
        unit.addLuck(luck);
    }

    @Override
    protected void dispose(Tower unit) {
        AttackAbility attackAbility = unit.getAbility(AttackAbility.class);
        if (attackAbility != null) {
            attackAbility.setTargets(attackAbility.getTargets() - targets);
        }
        unit.addAttackSpeed(-attackSpeed);
        unit.addLuck(-luck);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Eight cursed arms";
    }

    @Override
    public String getLevelBonus() {
        return "+" + targets + " targets.\n" +
                format.percentWithSignAndUnit(attackSpeed) + " attack speed.\n" +
                format.percentWithSignAndUnit(luck) + " luck.";
    }
}
