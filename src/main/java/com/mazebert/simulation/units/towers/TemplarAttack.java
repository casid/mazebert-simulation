package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class TemplarAttack extends AttackAbility {
    public static final float CRIT_CHANCE = 1.0f;
    public static final float CRIT_CHANCE_PER_LEVEL = 0.01f;

    private final float CRIT_CHANCE_BONUS = 0.1f;
    private final float CRIT_DAMAGE_BONUS = 0.5f;

    private int attacks;
    private float critChanceBonus;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addCritChance(CRIT_CHANCE_BONUS);
        unit.addCritDamage(CRIT_DAMAGE_BONUS);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addCritChance(-CRIT_CHANCE_BONUS);
        unit.addCritDamage(-CRIT_DAMAGE_BONUS);
        super.dispose(unit);
    }

    @Override
    protected boolean onCooldownReached() {
        if (attacks == 2) {
            critChanceBonus = CRIT_CHANCE + getUnit().getLevel() * CRIT_CHANCE_PER_LEVEL;
            getUnit().addCritChance(critChanceBonus);
            attacks = 0;
        }

        if (super.onCooldownReached()) {
            if (critChanceBonus > 0) {
                getUnit().addCritChance(-critChanceBonus);
                critChanceBonus = 0;
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
        return "True believer";
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
        return format.percentWithSignAndUnit(CRIT_CHANCE_PER_LEVEL) + " per level\n" +
            format.percentWithSignAndUnit(CRIT_CHANCE_BONUS) + " crit chance\n" +
            format.percentWithSignAndUnit(CRIT_DAMAGE_BONUS) + " crit damage\n";
    }
}
