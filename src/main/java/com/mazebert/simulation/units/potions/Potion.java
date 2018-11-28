package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.Ability;

import java.util.Arrays;
import java.util.List;

public strictfp abstract class Potion implements Card {
    private final List<Ability> abilities;

    public Potion(Ability... abilities) {
        this.abilities = Arrays.asList(abilities);
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

    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }
}
