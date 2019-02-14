package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class WoodenStaff extends Item {
    public WoodenStaff() {
        super(new WoodenStaffAbility());
    }

    @Override
    public String getName() {
        return "Wooden Staff";
    }

    @Override
    public String getDescription() {
        return "This staff was used by generations\nof shepherds to teach their sheep\nto knock it off.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0101_Wooden_staff_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
