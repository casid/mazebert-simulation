package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class Item implements Card {
    private final Ability<Tower>[] abilities;

    @SafeVarargs
    public Item(Ability<Tower> ... abilities) {
        this.abilities = abilities;
    }

    @Override
    public Rarity getDropRarity() {
        return getRarity();
    }

    @Override
    public boolean isDark() {
        return false;
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "casid";
    }

    public abstract String getIcon();

    public abstract int getItemLevel();

    public Ability<Tower>[] getAbilities() {
        return abilities;
    }
}
