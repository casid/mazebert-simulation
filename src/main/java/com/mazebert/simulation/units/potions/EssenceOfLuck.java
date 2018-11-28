package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class EssenceOfLuck extends Potion {
    public EssenceOfLuck() {
        super(new EssenceOfLuckAbility());
    }

    @Override
    public String getIcon() {
        return "9010_EssenceOfLuckPotion_512";
    }

    @Override
    public String getName() {
        return "Essence of Luck";
    }

    @Override
    public String getDescription() {
        return "Distilled by a master alchemist, this potion will turn your luck around.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getAuthor() {
        return "jhoijhoi";
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }
}
