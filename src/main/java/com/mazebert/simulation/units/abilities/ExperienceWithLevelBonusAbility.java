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

    @Override
    protected String getAttributeName() {
        return "experience";
    }
}
