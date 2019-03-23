package com.mazebert.simulation.errors;

public strictfp class UnsupportedVersionException extends RuntimeException {
    public UnsupportedVersionException(String message) {
        super(message);
    }
}
