package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkMeatMallet extends Item {

    public DarkMeatMallet() {
        super(new MeatMalletAbility(), new DarkItemAbility());
    }

    @Override
    public String getIcon() {
        return "0094_One_Handed_Hammer_512";
    }

    @Override
    public String getName() {
        return "Dark Meat Mallet";
    }

    @Override
    public String getDescription() {
        return "A cursed meat mallet, forged in the fires of the Dark Forge.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 45; // A bit lower than the original mallet!
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public Element getElement() {
        return Element.Darkness;
    }
}
