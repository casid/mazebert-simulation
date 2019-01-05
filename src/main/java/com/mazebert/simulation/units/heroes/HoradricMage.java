package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class HoradricMage extends Hero {

    public HoradricMage() {
        addAbility(new HoradricMageAbility());
    }

    @Override
    public String getName() {
        return "The 'horadric' Mage";
    }

    @Override
    public String getDescription() {
        return "He's not horadric and he's not even a mage, but he can build everything out of a pen and a string.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "0024_stash_512";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}