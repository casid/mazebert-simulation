package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Unit;

public abstract strictfp class Hero extends Unit implements Card {

    private final HeroType type;

    public Hero() {
        type = HeroType.forHero(this);
    }

    @Override
    public HeroType getType() {
        return type;
    }

    @Override
    public Rarity getDropRarity() {
        return getRarity();
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Andy";
    }

    public abstract String getIcon();

    @Override
    public String getModelId() {
        return null;
    }

    protected String getWizardLevelRequirementText() {
        return "<c=#fff8c6>Required wizard level: " + getItemLevel() + "</c>";
    }
}
