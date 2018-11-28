package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public class DrinkAll extends TutorialPotion {
    @Override
    public String getDescription() {
        return "You can drink an entire stack of potions. Press and hold the drink button.\n\nTry it with these drinks!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }
}
