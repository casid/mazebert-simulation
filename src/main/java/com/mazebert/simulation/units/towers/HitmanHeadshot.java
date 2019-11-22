package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class HitmanHeadshot extends Ability<Tower> {
    private final float critChanceBonus = 0.25f;
    private final float critDamageBonus = 0.25f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addCritChance(critChanceBonus);
        unit.addCritDamage(critDamageBonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addCritChance(-critChanceBonus);
        unit.addCritDamage(-critDamageBonus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Headshot";
    }

    @Override
    public String getDescription() {
        return "Sometimes a bullet hits the sweet spot between the eyes.";
    }

    @Override
    public String getIconFile() {
        return "bullet_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 25% crit chance\n+ 25% crit damage";
    }
}
