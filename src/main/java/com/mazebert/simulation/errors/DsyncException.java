package com.mazebert.simulation.errors;

public class DsyncException extends RuntimeException {
    private final int lastValidTurnNumber;

    public DsyncException(String message, int lastValidTurnNumber) {
        super(message);
        this.lastValidTurnNumber = lastValidTurnNumber;
    }

    public int getLastValidTurnNumber() {
        return lastValidTurnNumber;
    }
}
