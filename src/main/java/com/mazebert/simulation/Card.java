package com.mazebert.simulation;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.units.abilities.Ability;

public interface Card {
    String getName();

    String getDescription();

    String getAuthor();

    Rarity getRarity();

    Rarity getDropRarity();

    boolean isDropable();

    int getItemLevel();

    boolean isDark();

    default boolean isForgeable() {
        return true;
    }

    default boolean isSupporterReward() {
        return false;
    }

    default boolean isTradingAllowed() {
        return true;
    }

    default boolean isHarmful() {
        return false;
    }

    String getSinceVersion();

    void forEachAbility(Consumer<Ability> consumer);
}
