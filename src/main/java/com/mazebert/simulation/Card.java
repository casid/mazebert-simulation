package com.mazebert.simulation;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.changelog.Changelog;
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

    default Element getElement() {
        return Element.Unknown;
    }

    default boolean isDark() {
        return getElement() == Element.Darkness;
    }

    default boolean isLight() {
        return getElement() == Element.Light;
    }

    default boolean isEldritch() {
        return false;
    }

    default boolean isProphecy() {
        return false;
    }

    default boolean isForgeable() {
        return !isBlackMarketOffer();
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

    Changelog getChangelog();

    void forEachAbility(Consumer<Ability> consumer);

    default boolean isUniqueDrop() {
        return getRarity() == Rarity.Unique || getRarity() == Rarity.Legendary;
    }

    default boolean isUniqueInstance() {
        return isUniqueDrop();
    }

    default boolean isTransferable() {
        return !isInternal();
    }
}
