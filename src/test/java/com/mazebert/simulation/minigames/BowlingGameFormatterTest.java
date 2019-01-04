package com.mazebert.simulation.minigames;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameFormatterTest {
    private BowlingGameTrainer game;
    private BowlingGameFormatter formatter;

	@BeforeEach
    public void setUp()
    {
        game = new BowlingGameTrainer(new BowlingGame());
        formatter = new BowlingGameFormatter();
    }

	@Test
    public void testFormat_newGame()
    {
        thenFormattedFramesAre("--  --  --  --  --  --  --  --  --  --");
    }

	@Test
    public void testFormat_gutterGame()
    {
        game.givenGutterGame();
        thenFormattedFramesAre("00  00  00  00  00  00  00  00  00  00");
    }

	@Test
    public void testFormat_allOnes()
    {
        game.givenAllOnes();
        thenFormattedFramesAre("11  11  11  11  11  11  11  11  11  11");
    }

	@Test
    public void testFormat_oneSpare()
    {
        game.givenPinsAreRolled(3, 7, 5);
        thenFormattedFramesAre("3/  5-  --  --  --  --  --  --  --  --");
    }

	@Test
    public void testFormat_oneStrike()
    {
        game.givenPinsAreRolled(10, 3, 4);
        thenFormattedFramesAre("X  34  --  --  --  --  --  --  --  --");
    }

	@Test
    public void testFormat_perfectGame()
    {
        game.givenPerfectGame();
        thenFormattedFramesAre("X  X  X  X  X  X  X  X  X  XXX");
    }

	@Test
    public void testFormat_integrationTest()
    {
        game.givenPinsAreRolled(8, 2, 1, 9, 6, 0, 0, 0, 10, 10, 9, 1, 3, 7, 6, 2, 2, 8, 10);
        thenFormattedFramesAre("8/  1/  60  00  X  X  9/  3/  62  2/X");
    }

	@Test
    public void testFormat_allOnesButStrikeAtLastFrame()
    {
        game.givenSamePinsAreRolled(1, 18);
        game.givenPinsAreRolled(10, 1, 1);
        thenFormattedFramesAre("11  11  11  11  11  11  11  11  11  X11");
    }

	@Test
    public void testRoll_impossibleRollsArePreventedInLastFrame()
    {
        game.givenSamePinsAreRolled(1, 18);
        game.givenPinsAreRolled(10, 9, 1);
        thenFormattedFramesAre("11  11  11  11  11  11  11  11  11  X9/");
    }

    private void thenFormattedFramesAre(String expected)
    {
        assertThat(formatter.formatFrames(game.game)).isEqualTo(expected);
    }
}