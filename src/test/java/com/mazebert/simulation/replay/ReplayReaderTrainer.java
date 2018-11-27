package com.mazebert.simulation.replay;

import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;

import java.util.ArrayDeque;
import java.util.Queue;

public class ReplayReaderTrainer implements ReplayReader {

    private Queue<ReplayFrame> turns = new ArrayDeque<>();

    @Override
    public ReplayHeader readHeader() {
        return null;
    }

    @Override
    public ReplayFrame readFrame() {
        if (turns.isEmpty()) {
            return null;
        }
        return turns.remove();
    }

    @Override
    public void close() {

    }

    public void givenTurn(ReplayFrame turn) {
        turns.add(turn);
    }
}