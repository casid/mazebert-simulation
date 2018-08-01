package com.mazebert.simulation.messages;

import org.jusecase.builders.Builder;

@javax.annotation.Generated(value="jusecase-builders-generator")
public interface ResendTurnBuilderMethods<T extends ResendTurn, B extends Builder> extends Builder<T> {
    @Override
    default T build() {
        return getEntity();
    }

    T getEntity();

    default B withMessageNumber(byte value) {
        getEntity().setMessageNumber(value);
        return (B)this;
    }

    default B withTurnNumber(int value) {
        getEntity().turnNumber = value;
        return (B)this;
    }
}
