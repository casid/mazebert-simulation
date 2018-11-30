package com.mazebert.simulation.units.items;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp abstract class Item implements Card {
    private final Ability[] abilities;

    public Item(Ability... abilities) {
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

    @Override
    public void forEachAbility(Consumer<Ability> consumer) {
        for (Ability ability : abilities) {
            consumer.accept(ability);
        }
    }
}
