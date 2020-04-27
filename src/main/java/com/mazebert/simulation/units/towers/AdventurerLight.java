package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.ItemChanceWithLevelBonusAbility;

public strictfp class AdventurerLight extends ItemChanceWithLevelBonusAbility {

    public AdventurerLight() {
        super(0.2f, 0.004f);
    }

    @Override
    public String getTitle() {
        return "Adventurer's Treasure";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus) + " item chance.";
    }

    @Override
    public String getIconFile() {
        return "08_map_512";
    }
}
