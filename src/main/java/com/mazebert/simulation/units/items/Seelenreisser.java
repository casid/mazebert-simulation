package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Seelenreisser extends Item {

    public Seelenreisser() {
        super(new SeelenreisserAbility());
    }

    @Override
    public String getName() {
        return "Seelenreisser";
    }

    @Override
    public String getDescription() {
        return "This blade is consecrated to Soltar,\nGod of Darkness.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getIcon() {
        return "seelenreisser_512";
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public boolean isDark() {
        return true;
    }
}
