package com.mazebert.simulation;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.units.abilities.Ability;

public interface Card {
    CardType getType();

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

    default boolean isBlackMarketOffer() {
        return false;
    }

    default boolean isTradingAllowed() {
        return true;
    }

    default boolean isHarmful() {
        return false;
    }

    default boolean isInternal() {
        return false;
    }

    default boolean isSet() {
        return false;
    }

    String getSinceVersion();

    void forEachAbility(Consumer<Ability> consumer);

    default boolean isUniqueDrop() {
        return getRarity() == Rarity.Unique || getRarity() == Rarity.Legendary;
    }
}
