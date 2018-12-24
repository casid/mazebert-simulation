package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class ImpatienceWrathWatch extends Item {

    public ImpatienceWrathWatch() {
        super(new ImpatienceWrathWatchAbility(), new ImpatienceWrathSetAbility());
    }

    @Override
    public String getName() {
        return "Stressful Wristwatch";
    }

    @Override
    public String getDescription() {
        return "Tick tack...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getIcon() {
        return "fabric_roll_512";
    }

    @Override
    public int getItemLevel() {
        return 13;
    }

    @Override
    public String getAuthor() {
        return "Moknahr";
    }
}
