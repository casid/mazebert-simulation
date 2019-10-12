package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class TemplarAttack extends AttackAbility {
    public static final float CRIT_CHANCE = 1.0f;
    public static final float CRIT_CHANCE_PER_LEVEL = 0.01f;

    private int attacks;

    @Override
    protected boolean onCooldownReached() {
        float critChanceBonus = 0;
        if (attacks == 2) {
            critChanceBonus = CRIT_CHANCE + getUnit().getLevel() * CRIT_CHANCE_PER_LEVEL;
            getUnit().addCritChance(critChanceBonus);
        }

        if (super.onCooldownReached()) {
            if (critChanceBonus > 0) {
                getUnit().addCritChance(-critChanceBonus);
                attacks = 0;
            } else {
                ++attacks;
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "God's gift";
    }

    @Override
    public String getDescription() {
        return "Every third hit has " + format.percentWithSignAndUnit(CRIT_CHANCE) + " crit chance.";
    }

    @Override
    public String getIconFile() {
        return "0049_power_512";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(CRIT_CHANCE_PER_LEVEL) + " per level";
    }
}
