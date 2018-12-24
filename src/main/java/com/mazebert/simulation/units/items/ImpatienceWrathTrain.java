package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class ImpatienceWrathTrain extends Item {

    public ImpatienceWrathTrain() {
        super(new ImpatienceWrathTrainAbility(), new ImpatienceWrathSetAbility());
    }

    @Override
    public String getName() {
        return "Last Train of the Day";
    }

    @Override
    public String getDescription() {
        return "Choo Choo...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getIcon() {
        return "mechanical_leftovers_512";
    }

    @Override
    public int getItemLevel() {
        return 23;
    }

    @Override
    public String getAuthor() {
        return "Moknahr";
    }
}
