package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class RareSteak extends Item {

    public RareSteak() {
        super(new RareSteakCritAbility(), new RareSteakDamageAbility());
    }

    @Override
    public String getName() {
        return "Rare T-Bone Steak";
    }

    @Override
    public String getDescription() {
        return "This steak is bloody like hell. A steak for true butchers.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0063_meat_512";
    }

    @Override
    public int getItemLevel() {
        return 58;
    }
}
