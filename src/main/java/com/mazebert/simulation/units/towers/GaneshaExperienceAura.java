package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class GaneshaExperienceAura extends AuraAbility<Tower, Tower> {
    public GaneshaExperienceAura() {
        super(CardCategory.Tower, Tower.class, 3);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new GaneshaExperienceAuraEffect(getUnit()));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        GaneshaExperienceAuraEffect ability = unit.getAbility(GaneshaExperienceAuraEffect.class, getUnit());
        if (ability != null) {
            unit.removeAbility(ability);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Wisdom of the gods";
    }

    @Override
    public String getDescription() {
        return "Allies in 3 range gain 20% more experience.";
    }

    @Override
    public String getIconFile() {
        return "0013_flowers_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 0.5% experience gain per level.";
    }
}
