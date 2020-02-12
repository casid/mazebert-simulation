package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkFistfulOfSteel extends Item {

    public DarkFistfulOfSteel() {
        super(new FistfulOfSteelAbility(), new DarkItemAbility());
    }

    @Override
    public String getName() {
        return "Dark Fistful of Steel";
    }

    @Override
    public String getDescription() {
        return "A cursed Fistful of Steel, forged in the fires of the Dark Forge.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getIcon() {
        return "0006_handarmor_512";
    }

    @Override
    public int getItemLevel() {
        return 62;
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
