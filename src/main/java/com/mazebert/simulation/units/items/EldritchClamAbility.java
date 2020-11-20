package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchClamAbility extends StackableAbility<Tower> {

    public static final float itemBonus = 0.5f;
    public static final float goldBonus = 0.25f;
    public static final float luckBonus = -0.2f;

    private float item;
    private float gold;
    private float luck;

    @Override
    protected void updateStacks() {
        getUnit().addItemChance(-item);
        getUnit().addGoldModifer(-gold);
        getUnit().addLuck(-luck);

        float multiplier = getMultiplier();
        item = multiplier * itemBonus;
        gold = multiplier * goldBonus;
        luck = multiplier * luckBonus;

        getUnit().addItemChance(item);
        getUnit().addGoldModifer(gold);
        getUnit().addLuck(luck);
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
        return "Cursed Wealth";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(itemBonus) + " item chance.\n" +
                format.percentWithSignAndUnit(goldBonus) + " " + getCurrency().pluralLowercase + ".\n" +
                format.percentWithSignAndUnit(luckBonus) + " luck.";
    }
}
