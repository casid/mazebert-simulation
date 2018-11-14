package com.mazebert.simulation;

import java.util.UUID;

public interface Card {
    String getName();
    String getDescription();
    String getAuthor();
    Rarity getRarity();
    Rarity getDropRarity();
    boolean isDark();
    String getSinceVersion();
}
