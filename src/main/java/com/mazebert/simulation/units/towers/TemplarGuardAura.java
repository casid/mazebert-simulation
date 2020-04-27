package com.mazebert.simulation.units.towers;

public strictfp class TemplarGuardAura extends GuardAura {
    private final float CRIT_CHANCE_BONUS = 0.04f;
    private final float CRIT_DAMAGE_BONUS = 0.2f;

    public TemplarGuardAura(float range) {
        super(range);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
    }

    @Override
    protected void dispose(Tower unit) {
        super.dispose(unit);
    }

    @Override
    protected void adjustGuardBonus(float sign) {
        super.adjustGuardBonus(sign);
        getUnit().addCritChance(sign * CRIT_CHANCE_BONUS);
        getUnit().addCritDamage(sign * CRIT_DAMAGE_BONUS);
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(CRIT_CHANCE_BONUS) + " crit chance.\n" +
                format.percentWithSignAndUnit(CRIT_DAMAGE_BONUS) + " crit damage.";
    }
}
