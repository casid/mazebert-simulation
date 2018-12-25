package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class LoanShark extends Hero {

    public LoanShark() {
        addAbility(new LoanSharkAbility());
    }

    @Override
    public String getName() {
        return "Loan Shark";
    }

    @Override
    public String getDescription() {
        return "We can loan you enough money to get you completely out of debt.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "drowning_512";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "1.1";
    }
}
