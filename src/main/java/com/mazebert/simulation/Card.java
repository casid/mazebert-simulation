package com.mazebert.simulation;

import java.util.UUID;

public interface Card {
    String getName();
    String getDescription();
    String getAuthor();
    Rarity getRarity();
    boolean isDark();
    String getSinceVersion();

    UUID getCardId();
    void setCardId(UUID id);
}
