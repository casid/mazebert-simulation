package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hashable;

public interface CardType<T extends Card> extends Hashable {
    int id();
    T instance();
    T create();
    @SuppressWarnings("unused") // Used by ui
    CardCategory category();
}
