package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class JesterKing extends Hero {

    public JesterKing() {
        addAbility(new JesterKingAbility());
    }

    @Override
    public String getName() {
        return "Osmo, the Jester King";
    }

    @Override
    public String getDescription() {
        return "Better lucky than skilled: My solemn vow.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "hood_512";
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
        return "Cody Dwyer";
    }
}
