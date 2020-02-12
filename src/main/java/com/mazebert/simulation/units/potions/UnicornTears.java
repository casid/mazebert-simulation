package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;

public strictfp class UnicornTears extends Potion {

    private final UnicornTearsAbility unicornTearsAbility;

    public UnicornTears() {
        super(new UnicornTearsAbility());
        unicornTearsAbility = getAbility(0, UnicornTearsAbility.class);
    }

    @Override
    public String getIcon() {
        return "9009_WisdomPotion";
    }

    @Override
    public String getName() {
        return "Unicorn Tears";
    }

    @Override
    public String getDescription() {
        return "Tears of the last unicorn.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
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
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }

    public void setLevels(int levels) {
        unicornTearsAbility.setLevels(levels);
    }

    @Override
    public Potion copy() {
        UnicornTears copy = (UnicornTears)super.copy();
        if (Sim.context().version >= Sim.v19) {
            copy.setLevels(unicornTearsAbility.getLevels());
        }
        return copy;
    }
}
