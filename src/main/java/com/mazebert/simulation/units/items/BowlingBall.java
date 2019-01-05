package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class BowlingBall extends Item {

    public BowlingBall() {
        super(new BowlingBallRollAbility());
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
}
