package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class Kvothe extends Hero {

    public Kvothe() {
        addAbility(new KvotheAbility());
    }

    @Override
    public String getName() {
        return "Kvothe, the Arcane";
    }

    @Override
    public String getDescription() {
        return "\"You never do things the easy way,\n do you?\" - \"There's an easy way?\"\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getIcon() {
        return "hard_mode_hero_512";
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "1.1";
    }
}
