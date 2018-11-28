package com.mazebert.simulation;

import com.mazebert.simulation.units.abilities.Ability;

import java.util.List;

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

    String getSinceVersion();

    List<Ability> getAbilities();
}
