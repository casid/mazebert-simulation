package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class NorlsFurySword extends Item {

    public NorlsFurySword() {
        super(new NorlsFurySwordAbility(), new NorlsFurySetAbility());
    }

    @Override
    public String getName() {
        return "Norls Steel";
    }

    @Override
    public String getDescription() {
        return "A long forgotten sword, once worn by Norl, an ancient hero of the North. Some of its former powers are still inside this blade...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getIcon() {
        return "0020_magicweapon_512";
    }

    @Override
    public int getItemLevel() {
        return 10;
    }
}
