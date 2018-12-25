package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class Roderic extends Hero {

    public Roderic() {
        addAbility(new RodericAbility());
    }

    @Override
    public String getName() {
        return "Lord Roderic";
    }

    @Override
    public String getDescription() {
        return "Life before death,\nstrength before weakness,\njourney before destination.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "0049_power_512";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }
}
