package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.RestInPieceAbility;

public strictfp class LuciferRestInPiece extends RestInPieceAbility {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Eternal piece";
    }

    @Override
    public String getDescription() {
        return "Damaged creeps rest in piece.";
    }

    @Override
    public String getIconFile() {
        return "blood_demon_blade_512"; // TODO
    }
}
