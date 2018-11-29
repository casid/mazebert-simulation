package com.mazebert.simulation.units.potions;

public abstract strictfp class TutorialPotion extends Potion {
    @Override
    public String getName() {
        return "A Drink from the Developer";
    }

    @Override
    public String getIcon() {
        return "cup_512";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
