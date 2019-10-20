package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.RestInPieceAbility;

public strictfp class LuciferFallenRestInPiece extends RestInPieceAbility {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Eternal hell";
    }

    @Override
    public String getDescription() {
        return "Damaged creeps rest in hell.";
    }

    @Override
    public String getIconFile() {
        return "blood_demon_blade_512"; // TODO
    }
}
