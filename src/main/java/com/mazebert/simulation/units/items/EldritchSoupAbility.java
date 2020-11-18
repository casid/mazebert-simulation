package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchSoupAbility extends AuraAbility<Tower, Tower> {
    private static final float potionBonus = 0.1f;
    private static final float luckBonus = -0.1f;

    public EldritchSoupAbility() {
        super(CardCategory.Item, Tower.class, 1);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addPotionEffectiveness(potionBonus * unit.getEldritchCardModifier());
        unit.addLuck(luckBonus * unit.getEldritchCardModifier());
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        unit.addPotionEffectiveness(-potionBonus * unit.getEldritchCardModifier());
        unit.addLuck(-luckBonus * unit.getEldritchCardModifier());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Cursed Soup";
    }

    @Override
    public String getDescription() {
        return "Towers in 1 range gain:";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(potionBonus) + " effect of consumed potions.\n" +
                format.percentWithSignAndUnit(luckBonus) + " luck.";
    }
}
