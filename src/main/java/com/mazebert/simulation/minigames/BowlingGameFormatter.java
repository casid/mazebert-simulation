package com.mazebert.simulation.minigames;

public strictfp class BowlingGameFormatter {

    public String formatFrames(BowlingGame game) {
        StringBuilder result = new StringBuilder();
        int rollIndex = 0;

        for (int frame = 0; frame < 12; ++frame) {
            if (frame < 10 || rollIndex <= game.getLastRollIndex()) {
                if (game.isStrike(rollIndex)) {
                    result.append('X');
                    rollIndex += 1;
                } else if (game.isSpare(rollIndex)) {
                    result.append(getRoll(game, rollIndex));
                    result.append('/');
                    rollIndex += 2;
                } else {
                    result.append(getRoll(game, rollIndex));
                    result.append(getRoll(game, rollIndex + 1));
                    rollIndex += 2;
                }

                result.append(getSpacing(frame));
            }
        }

        return result.toString();
    }

    private char getRoll(BowlingGame game, int rollIndex) {
        if (rollIndex <= game.getLastRollIndex()) {
            return (char) ('0' + game.getRoll(rollIndex));
        }

        return '-';
    }

    private String getSpacing(int frame) {
        return frame < 9 ? "  " : "";
    }
}
