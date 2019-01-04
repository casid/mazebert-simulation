package com.mazebert.simulation.minigames;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BowlingGameTest {
    private BowlingGameTrainer game;

    @BeforeEach
    public void setUp() {
        game = new BowlingGameTrainer(new BowlingGame());
    }

    @Test
    public void testScore_gutterGame() {
        game.givenGutterGame();
        game.thenScoreIs(0);
    }

    @Test
    public void testScore_allOnes() {
        game.givenAllOnes();
        game.thenScoreIs(20);
    }

    @Test
    public void testScore_oneSpare() {
        game.givenPinsAreRolled(5, 5, 3);
        game.thenScoreIs(16);
    }

    @Test
    public void testScore_oneStrike() {
        game.givenPinsAreRolled(10, 3, 4);
        game.thenScoreIs(24);
    }

    @Test
    public void testScore_perfectGame() {
        game.givenPerfectGame();
        game.thenScoreIs(300);
    }

    @Test
    public void testScore_allOnesButStrikeAtLastFrame() {
        game.givenSamePinsAreRolled(1, 18);
        game.givenPinsAreRolled(10, 1, 1);
        game.thenScoreIs(30);
    }

    @Test
    public void testScore_integrationTest() {
        game.givenPinsAreRolled(8, 2, 1, 9, 6, 0, 0, 0, 10, 10, 9, 1, 3, 7, 6, 2, 2, 8, 10);
        game.thenScoreIs(139);
    }

    @Test
    public void testFinished_newGame() {
        game.thenGameIsNotFinished();
    }

    @Test
    public void testFinished_gutterGame() {
        game.givenGutterGame();
        game.thenGameIsFinished();
    }

    @Test
    public void testFinished_perfectGame() {
        game.givenPerfectGame();
        game.thenGameIsFinished();
    }

    @Test
    public void testFinished_allOnesButLastFrame() {
        game.givenSamePinsAreRolled(1, 19);
        game.thenGameIsNotFinished();
    }

    @Test
    public void testFinished_allOnesButStrikeAtLastFrame() {
        game.givenSamePinsAreRolled(1, 18);
        game.givenPinsAreRolled(10);
        game.thenGameIsNotFinished();
    }

    @Test
    public void testFinished_allOnesButSpareAtLastFrame() {
        game.givenSamePinsAreRolled(1, 19);
        game.givenPinsAreRolled(9);
        game.thenGameIsNotFinished();
    }

    @Test
    public void testRoll_rollsAreTruncated() {
        game.givenPinsAreRolled(12, 13, 144, -12, -55, -1333);
        game.thenScoreIs(60);
    }

    @Test
    public void testRoll_impossibleRollsArePrevented() {
        game.givenPinsAreRolled(9, 7);
        game.thenScoreIs(10);
    }

    @Test
    public void testRoll_impossibleRollsArePreventedInLastFrame() {
        game.givenSamePinsAreRolled(1, 18);
        game.givenPinsAreRolled(10, 9, 7);
        game.thenScoreIs(18 + 10 + 9 + 1);
    }

    @Test
    public void testRoll_finishedGameIsReset() {
        game.givenPerfectGame();
        game.givenPinsAreRolled(1);
        game.thenScoreIs(1);
    }
}