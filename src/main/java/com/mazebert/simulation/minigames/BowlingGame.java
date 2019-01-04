package com.mazebert.simulation.minigames;

public strictfp class BowlingGame {
    private int rollAttemptInFrame;
    private int remainingPins;
    private int currentRoll;
    private int[] rolls;

    public BowlingGame() {
        reset();
    }

    public void reset() {
        rollAttemptInFrame = 0;
        remainingPins = 10;
        currentRoll = 0;
        rolls = new int[21];
    }

    public void roll(int pins) {
        if (isFinished()) {
            reset();
        }

        if (pins > remainingPins) {
            pins = remainingPins;
        } else if (pins < 0) {
            pins = 0;
        }

        rolls[currentRoll++] = pins;
        remainingPins -= pins;
        ++rollAttemptInFrame;

        if (remainingPins <= 0 || rollAttemptInFrame > 1) {
            remainingPins = 10;
            rollAttemptInFrame = 0;
        }
    }

    public int getScore() {
        int score = 0;
        int rollIndex = 0;
        for (int frame = 0; frame < 10; ++frame) {
            if (isStrike(rollIndex)) {
                score += 10 + getRoll(rollIndex + 1) + getRoll(rollIndex + 2);
                rollIndex += 1;
            } else if (isSpare(rollIndex)) {
                score += 10 + getRoll(rollIndex + 2);
                rollIndex += 2;
            } else {
                score += getRoll(rollIndex) + getRoll(rollIndex + 1);
                rollIndex += 2;
            }
        }
        return score;
    }

    public boolean isFinished() {
        return getFinishedFrames() == 10;
    }

    private int getFinishedFrames() {
        int frames = 0;
        int extraRollsOnLastFrame = 0;
        for (int rollIndex = 0; rollIndex < currentRoll; ++rollIndex) {
            if (frames < 10) {
                if (isStrike(rollIndex)) {
                    ++frames;
                    extraRollsOnLastFrame = 2;
                } else if (rollIndex + 1 < currentRoll) {
                    if (isSpare(rollIndex)) {
                        extraRollsOnLastFrame = 1;
                    }

                    ++frames;
                    ++rollIndex;
                }
            } else {
                --extraRollsOnLastFrame;
            }
        }

        if (frames == 10 && extraRollsOnLastFrame > 0) {
            frames = 9;
        }

        return frames;
    }

    public int getRoll(int rollIndex) {
        return rollIndex < currentRoll ? rolls[rollIndex] : 0;
    }

    public boolean isSpare(int rollIndex) {
        return getRoll(rollIndex) + getRoll(rollIndex + 1) == 10;
    }

    public boolean isStrike(int rollIndex) {
        return getRoll(rollIndex) == 10;
    }

    public int getLastRollIndex() {
        return currentRoll - 1;
    }
}
