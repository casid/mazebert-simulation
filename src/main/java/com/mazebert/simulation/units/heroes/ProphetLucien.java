package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class ProphetLucien extends Hero {

    public ProphetLucien() {
        addAbility(new ProphetLucienAbility());
    }

    @Override
    public String getIcon() {
        return "dragonhead_512"; // TODO
    }

    @Override
    public String getName() {
        return "Prophet Lucien";
    }

    @Override
    public String getDescription() {
        return "I am the prophet of fortune.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public boolean isLight() {
        return true;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }
}
