package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hashable;

public interface CardType<T> extends Hashable {
    T instance();
    T create();
}
