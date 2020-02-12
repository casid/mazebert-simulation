package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkBabySword extends Item {

    public DarkBabySword() {
        super(new BabySwordAbility(), new DarkItemAbility());
    }

    @Override
    public String getName() {
        return "Dark Baby Sword";
    }

    @Override
    public String getDescription() {
        return "A cursed baby sword, forged in the fires of the Dark Forge.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
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
        return 1;
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
