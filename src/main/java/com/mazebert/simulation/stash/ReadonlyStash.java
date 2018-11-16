package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;

public strictfp interface ReadonlyStash<T extends Card> {
    int size();
    ReadonlyStashEntry<T> get(int index);
}
