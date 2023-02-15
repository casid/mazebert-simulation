package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class FrogPoisonArmorAbility extends Ability<Tower> {

    private final float armorPenetration = 0.3f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addArmorPenetration(armorPenetration);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addArmorPenetration(-armorPenetration);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Toxic decomposition";
    }

    @Override
    public String getDescription() {
        return "Poison damage penetrates " + format.percent(armorPenetration) + "% armor.";
    }

    @Override
    public String getIconFile() {
        return "0066_poisonspell_512";
    }
}
