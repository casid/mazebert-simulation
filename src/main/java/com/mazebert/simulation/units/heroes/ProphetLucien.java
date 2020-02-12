package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;

public strictfp class ProphetLucien extends Hero {

    public ProphetLucien() {
        addAbility(new ProphetLucienAbility());
    }

    @Override
    public String getIcon() {
        return "0040_holyman_512";
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
    public Element getElement() {
        return Element.Light;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }
}
