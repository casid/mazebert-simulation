package com.mazebert.simulation;

public interface Card {
    String getName();

    String getDescription();

    String getAuthor();

    Rarity getRarity();

    Rarity getDropRarity();

    boolean isDropable();

    boolean isDark();

    String getSinceVersion();
}
