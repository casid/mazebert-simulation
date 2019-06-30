package com.mazebert.simulation.units.abilities;

public abstract strictfp class ExperienceWithLevelBonusAbility extends AttributeWithLevelBonusAbility {
    public ExperienceWithLevelBonusAbility(float bonus, float bonusPerLevel) {
        super(bonus, bonusPerLevel);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addExperienceModifier(amount);
    }

    public String getTitle() {
        return "More experience!";
    }

    public String getDescription() {
        return "The carrier of this item gains " + format.percent(bonus) + "% more experience.";
    }

    @Override
    public String getLevelBonus() {
        if (bonusPerLevel <= 0.0f) {
            return null;
        }
        return format.percentWithSignAndUnit(bonusPerLevel) + " experience bonus per level.";
    }
}
