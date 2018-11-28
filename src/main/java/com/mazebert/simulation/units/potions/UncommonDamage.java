package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class UncommonDamage extends Potion {
    public UncommonDamage() {
        super(new CommonDamageAbility());
    }

    @Override
    public String getIcon() {
        return "9002_DamagePotion";
    }

    @Override
    public String getName() {
        return "Potion of Strength";
    }

    @Override
    public String getDescription() {
        return "Violence is the last refuge of the incompetent. Cheers!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 8;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
