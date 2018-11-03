package com.mazebert.simulation;

import java.util.UUID;

public interface Card {
    String getName();
    Rarity getRarity();

    UUID getCardId();
    void setCardId(UUID id);
}
