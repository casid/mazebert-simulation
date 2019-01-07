package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class JackInTheBox extends Hero {

    public JackInTheBox() {
        addAbility(new JackInTheBoxAbility());
    }

    @Override
    public String getName() {
        return "Jack in the Box";
    }

    @Override
    public String getDescription() {
        return "He's a lousy jack, that's why he's hiding in a box. He can summon goblins, though.\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "cage_512";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getAuthor() {
        return "a mysterious supporter";
    }
}
