package com.mazebert.simulation.units.potions;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp abstract class Potion implements Card {
    private final Ability[] abilities;
    private final PotionType type;

    public Potion(Ability... abilities) {
        this.abilities = abilities;
        this.type = PotionType.forClass(getClass());
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
        return "Andy";
    }

    public abstract String getIcon();

    @Override
    public void forEachAbility(Consumer<Ability> consumer) {
        for (Ability ability : abilities) {
            consumer.accept(ability);
        }
    }

    @SuppressWarnings("unused") // For user interface to show warning hint
    public boolean isDestructive() {
        return false;
    }

    @Override
    public PotionType getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public <T extends Ability> T getAbility(int index, Class<T> abilityClass) {
        return (T)abilities[index];
    }
}
