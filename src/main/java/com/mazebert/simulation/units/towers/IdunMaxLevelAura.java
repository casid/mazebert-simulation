package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class IdunMaxLevelAura extends AuraAbility<Tower, Tower> {
    public IdunMaxLevelAura() {
        super(CardCategory.Tower, Tower.class);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        if (isApplicable(unit)) {
            unit.addAbility(new IdunMaxLevelAuraEffect());
        }
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        if (isApplicable(unit)) {
            unit.removeAbility(IdunMaxLevelAuraEffect.class);
        }
    }

    private boolean isApplicable(Tower unit) {
        return unit.getElement() == Element.Nature && getUnit().getWizard() == unit.getWizard();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Golden Apples";
    }

    @Override
    public String getDescription() {
        return "The max level of your " + format.element(Element.Nature) + " towers in range is equal to half your current health.";
    }

    @Override
    public String getIconFile() {
        return "apple_512";
    }
}
