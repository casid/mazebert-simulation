package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class LittleFinger extends Hero {

    public LittleFinger() {
        addAbility(new LittleFingerAbility());
    }

    @Override
    public String getName() {
        return "Sir Littlefinger";
    }

    @Override
    public String getDescription() {
        return "I did warn you not to trust me,\nyou know.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getIcon() {
        return "metal_helmet_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }
}
