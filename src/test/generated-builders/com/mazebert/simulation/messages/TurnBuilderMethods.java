package com.mazebert.simulation.messages;

import org.jusecase.builders.Builder;

@javax.annotation.Generated(value="jusecase-builders-generator")
public interface TurnBuilderMethods<T extends Turn, B extends Builder> extends Builder<T> {
    @Override
    default T build() {
        return getEntity();
    }

    T getEntity();

    default B withMessageNumber(byte value) {
        getEntity().setMessageNumber(value);
        return (B)this;
    }

    default B withCommands(java.util.List value) {
        getEntity().commands = value;
        return (B)this;
    }

    default B withHash(int value) {
        getEntity().hash = value;
        return (B)this;
    }

    default B withPlayerId(int value) {
        getEntity().playerId = value;
        return (B)this;
    }

    default B withTurnNumber(int value) {
        getEntity().turnNumber = value;
        return (B)this;
    }
}
