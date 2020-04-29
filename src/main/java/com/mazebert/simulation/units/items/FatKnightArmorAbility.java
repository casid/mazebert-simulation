package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class FatKnightArmorAbility extends AuraAbility<Tower, Tower> {

    private static final int INVENTORY_SIZE = 1;
    private static final float ATTACK_SPEED = -0.25f;

    public FatKnightArmorAbility() {
        super(CardCategory.Item, Tower.class, 1);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addInventorySize(INVENTORY_SIZE);
        unit.addAttackSpeed(ATTACK_SPEED);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addAttackSpeed(-ATTACK_SPEED);
        unit.addInventorySize(-INVENTORY_SIZE);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Room for everyone";
    }

    @Override
    public String getDescription() {
        return "Towers in 1 range gain:";
    }

    @Override
    public String getLevelBonus() {
        return "+" + INVENTORY_SIZE + " inventory slot.\n" + format.percentWithSignAndUnit(ATTACK_SPEED) + " attack speed.";
    }
}
