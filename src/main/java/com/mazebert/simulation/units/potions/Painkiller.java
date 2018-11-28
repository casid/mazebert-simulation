package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;

public strictfp class Painkiller extends Potion {
    public Painkiller() {
        super(new PainkillerAbility());
    }

    @Override
    public String getIcon() {
        return "bomb_512";
    }

    @Override
    public String getName() {
        return "Painkiller";
    }

    @Override
    public String getDescription() {
        return "If you can't stand the pain any longer, this potion will help. Promised.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 68;
    }

    @Override
    public String getAuthor() {
        return "Thomas Pircher";
    }

    @Override
    public String getSinceVersion() {
        return "0.5";
    }

    @Override
    public boolean isDestructive() {
        return true;
    }
}
