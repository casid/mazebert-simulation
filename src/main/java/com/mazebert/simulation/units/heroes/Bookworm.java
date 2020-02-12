package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;

public strictfp class Bookworm extends Hero {

    public Bookworm() {
        addAbility(new BookwormBooksAbility());
        addAbility(new BookwormExperienceAbility());
    }

    @Override
    public String getIcon() {
        return "dragonhead_512";
    }

    @Override
    public String getName() {
        return "Bookworm";
    }

    @Override
    public String getDescription() {
        return "I know I don't know anything.\nDid you know that?\n" + getWizardLevelRequirementText();
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 24;
    }

    @Override
    public String getSinceVersion() {
        return "1.6";
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }
}
