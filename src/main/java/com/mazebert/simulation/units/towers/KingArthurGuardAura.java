package com.mazebert.simulation.units.towers;

public strictfp class KingArthurGuardAura extends GuardAura {
    public KingArthurGuardAura(float range) {
        super(range, 12);
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
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(KingArthurDamagePerLevel.DAMAGE_LEVEL_BONUS) + " damage per level.";
    }
}
