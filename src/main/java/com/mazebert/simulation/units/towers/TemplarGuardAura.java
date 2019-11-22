package com.mazebert.simulation.units.towers;

public strictfp class TemplarGuardAura extends GuardAura {
    private final float CRIT_CHANCE_BONUS = 0.1f;
    private final float CRIT_DAMAGE_BONUS = 0.5f;

    public TemplarGuardAura(float range) {
        super(range);
    }

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
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(CRIT_CHANCE_BONUS) + " crit chance\n" +
                format.percentWithSignAndUnit(CRIT_DAMAGE_BONUS) + " crit damage\n";
    }
}
