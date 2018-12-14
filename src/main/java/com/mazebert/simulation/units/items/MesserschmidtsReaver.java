package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class MesserschmidtsReaver extends Item {

    public MesserschmidtsReaver() {
        super(new MesserschmidtsReaverAbility());
    }

    @Override
    public String getName() {
        return "Messerschmidt's Reaver";
    }

    @Override
    public String getDescription() {
        return "The greatest battle axe ever forged. Very powerful... if one could ever lift it.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.5";
    }

    @Override
    public String getIcon() {
        return "0026_axeattack2_512";
    }

    @Override
    public int getItemLevel() {
        return 66;
    }

    @Override
    public String getAuthor() {
        return "hokkei";
    }
}
