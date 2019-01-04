package com.mazebert.simulation.minigames;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameTrainer {
    public final BowlingGame game;

    public BowlingGameTrainer(BowlingGame game) {
        this.game = game;
    }

    public void givenGutterGame() {
        givenSamePinsAreRolled(0, 20);
    }

    public void givenAllOnes() {
        givenSamePinsAreRolled(1, 20);
    }

    public void givenPerfectGame() {
        givenSamePinsAreRolled(10, 12);
    }

    public void givenSamePinsAreRolled(int pins, int times) {
        for (int i = 0; i < times; ++i) {
            game.roll(pins);
        }
    }

    public void givenPinsAreRolled(int... rolledPins) {
        for (int pins : rolledPins) {
            game.roll(pins);
        }
    }

    public void thenScoreIs(int expectedScore) {
        assertThat(game.getScore()).isEqualTo(expectedScore);
    }

    public void thenGameIsNotFinished() {
        assertThat(game.isFinished()).isFalse();
    }

    public void thenGameIsFinished() {
        assertThat(game.isFinished()).isTrue();
    }
}
