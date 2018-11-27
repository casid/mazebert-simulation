package com.mazebert.simulation.replay.data;

import com.mazebert.simulation.messages.Turn;

import java.util.ArrayList;
import java.util.List;

public strictfp class ReplayFrame {
    public int turnNumber;
    public ReplayTurn[] playerTurns;

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public List<Turn> getTurns() {
        List<Turn> result = new ArrayList<>(playerTurns.length);
        for (int i = 0; i < playerTurns.length; ++i) {
            result.add(ReplayTurn.toTurn(playerTurns[i], i + 1, turnNumber));
        }

        return result;
    }
}
