package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkBlade extends Item {

    public DarkBlade() {
        super(new DarkBladeAbility(), new DarkItemAbility());
    }

    @Override
    public String getName() {
        return "Blade of Darkness";
    }

    @Override
    public String getDescription() {
        return "The masterpiece of the Dark Forge.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getIcon() {
        return "0095_One_Handed_Sworld_512";
    }

    @Override
    public int getItemLevel() {
        return 66;
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
