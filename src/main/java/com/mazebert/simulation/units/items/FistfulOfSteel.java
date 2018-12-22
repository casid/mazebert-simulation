package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class FistfulOfSteel extends Item {

    public FistfulOfSteel() {
        super(new FistfulOfSteelAbility());
    }

    @Override
    public String getName() {
        return "Fistful of Steel";
    }

    @Override
    public String getDescription() {
        return "A .44 full of bullets\nFace full of pale\nEyes full of empty\nA stare full of nails.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }

    @Override
    public String getIcon() {
        return "0006_handarmor_512";
    }

    @Override
    public int getItemLevel() {
        return 62;
    }
}
