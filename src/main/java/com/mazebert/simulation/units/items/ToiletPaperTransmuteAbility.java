package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ToiletPaperTransmuteAbility extends Ability<Tower> {
    public static final int AMOUNT = 3;

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Prepper's " + getCurrency().pluralLowercase;
    }

    @Override
    public String getDescription() {
        return "Transmute to receive " + AMOUNT + " items of " + format.rarity(Rarity.Unique) + " or " + format.rarity(Rarity.Legendary) + " rarity.";
    }
}
