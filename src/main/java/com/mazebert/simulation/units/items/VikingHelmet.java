package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class VikingHelmet extends Item {

    public VikingHelmet() {
        super(new VikingHelmetLuckAbility(), new VikingHelmetDamageAbility(), new VikingHelmetAbility());
    }

    @Override
    public String getName() {
        return "Viking Helmet";
    }

    @Override
    public String getDescription() {
        return "Whoever wears this helmet, will look bad-ass.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getIcon() {
        return "viking_helmet_512";
    }

    @Override
    public int getItemLevel() {
        return 58;
    }

    @Override
    public String getAuthor() {
        return "glamrune";
    }
}