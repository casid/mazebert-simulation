package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class Potion implements Card {
    private final Ability<Tower>[] abilities;

    @SafeVarargs
    public Potion(Ability<Tower> ... abilities) {
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

    public Ability<Tower>[] getAbilities() {
        return abilities;
    }
}
