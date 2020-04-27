package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.GainItemOnBuildAbility;
import com.mazebert.simulation.units.items.BloodDemonBlade;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;

public strictfp class BloodDemonSummonBlade extends GainItemOnBuildAbility {

    private static final float healthReduction = 0.01f;
    private static final int bladeDamagePerLifeLost = 1;

    @Override
    protected Item createItem() {
        float healthToLose = getUnit().getWizard().health - healthReduction;
        getUnit().getWizard().addHealth(-healthToLose);

        BloodDemonBlade blade = new BloodDemonBlade();
        blade.setDamage(100.0f * bladeDamagePerLifeLost * healthToLose);

        return blade;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Bathe in Your Blood";
    }

    @Override
    public String getDescription() {
        return "When you build Blood Demon, your health is reduced to " + format.percent(healthReduction) + "%. In exchange, Blood Demon summons " + format.card(ItemType.BloodDemonBlade) + ".";
    }

    @Override
    public String getLevelBonus() {
        return "+" + bladeDamagePerLifeLost + " base damage on blade / life lost.";
    }

    @Override
    public String getIconFile() {
        return "blood_demon_blade_512";
    }
}
