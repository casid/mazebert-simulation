package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class RareDamage extends Potion {
    public RareDamage() {
        super(new RareDamageAbility());
    }

    @Override
    public String getIcon() {
        return "9002_DamagePotion";
    }

    @Override
    public String getName() {
        return "Great Potion of Strength";
    }

    @Override
    public String getDescription() {
        return "Violence is the last refuge of the incompetent. Cheers!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
