package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hashable;

public interface CardType<T extends Card> extends Hashable {
    T instance();
    T create();
}
