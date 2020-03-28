package com.mazebert.simulation.replay.data;

import com.mazebert.simulation.messages.Turn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public strictfp class ReplayFrame {
    public int turnNumber;
    public int hash;
    public ReplayTurn[] playerTurns;

    public List<Turn> getTurns() {
        if (playerTurns == null) {
            return Collections.emptyList();
        }

        List<Turn> result = new ArrayList<>(playerTurns.length);
        for (int i = 0; i < playerTurns.length; ++i) {
            if (playerTurns[i] == null) {
                break; // turn array is invalid at this point, try to resume with what we got.
            }
            result.add(ReplayTurn.toTurn(playerTurns[i], i + 1, turnNumber, hash));
        }

        return result;
    }
}
