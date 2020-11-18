package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class KnusperHexeAura extends AuraAbility<KnusperHexe, Creep> {

    private static final int baseReduction = 10;

    public KnusperHexeAura() {
        super(CardCategory.Tower, Creep.class, Sim.context().version >= Sim.vDoLEnd ? 0 : 3);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        KnusperHexeAuraEffect effect = unit.addAbilityStack(getUnit(), KnusperHexeAuraEffect.class);
        if (effect != null) {
            effect.setReduction(baseReduction + getUnit().getCreepsEaten());
        }
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
        return "Weakening Presence";
    }

    @Override
    public String getDescription() {
        return "Armor of creeps in range is reduced by " + baseReduction + ".";
    }

    @Override
    public String getIconFile() {
        return "0066_poisonspell_512";
    }

    @Override
    public String getLevelBonus() {
        return "+1 armor reduction per child eaten.";
    }
}
