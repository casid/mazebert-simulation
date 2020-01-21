package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.minigames.BowlingGame;

public strictfp class BowlingBall extends Item {
    public static final float DEATH_CHANCE = 0.3f;
    public static final float BALL_COOLDOWN = 40.0f;
    public static final int NOTIFICATION_COLOR = 0xbaa759;

    private final BowlingGame game = new BowlingGame();

    public BowlingBall() {
        super(new BowlingBallRollAbility(), new BowlingBallGameAbility());
        getAbility(BowlingBallRollAbility.class).setGame(game);
        getAbility(BowlingBallGameAbility.class).setGame(game);
    }

    @Override
    public String getName() {
        return "The Dude";
    }

    @Override
    public String getDescription() {
        return "Let's go bowling!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.2";
    }

    @Override
    public String getIcon() {
        return "bowling_ball_512";
    }

    @Override
    public int getItemLevel() {
        return 79;
    }

    @Override
    public String getAuthor() {
        return "Klaus Sobieray";
    }

    public BowlingGame getGame() {
        return game;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return Sim.context().version < Sim.v20;
    }
}
