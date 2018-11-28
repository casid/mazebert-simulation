package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public class CommonDamage extends Potion {
    public CommonDamage() {
        super(new CommonDamageAbility());
    }

    @Override
    public String getIcon() {
        return "9002_DamagePotion";
    }

    @Override
    public String getName() {
        return "Small Potion of Strength";
    }

    @Override
    public String getDescription() {
        return "Violence is the last refuge of the incompetent. Cheers!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
