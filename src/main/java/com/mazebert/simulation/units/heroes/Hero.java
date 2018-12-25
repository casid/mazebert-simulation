package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Unit;

public abstract strictfp class Hero extends Unit implements Card {

    @Override
    public Rarity getDropRarity() {
        return getRarity();
    }

    @Override
    public boolean isDark() {
        return false;
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
