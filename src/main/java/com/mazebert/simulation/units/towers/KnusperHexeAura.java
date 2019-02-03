package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class KnusperHexeAura extends AuraAbility<KnusperHexe, Creep> {

    private static final int baseReduction = 10;

    public KnusperHexeAura() {
        super(CardCategory.Tower, Creep.class, 3);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        KnusperHexeAuraEffect effect = unit.addAbilityStack(getUnit(), KnusperHexeAuraEffect.class);
        effect.setReduction(baseReduction + getUnit().getCreepsEaten());
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        unit.removeAbilityStack(getUnit(), KnusperHexeAuraEffect.class);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Weakening presence";
    }

    @Override
    public String getDescription() {
        return "The armor of creeps in 3 range is reduced by " + baseReduction + ".";
    }

    @Override
    public String getIconFile() {
        return "0066_poisonspell_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 1 armor reduction per child eaten.";
    }
}
