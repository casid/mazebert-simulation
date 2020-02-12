package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class Lightbringer extends Item {

    public Lightbringer() {
        super(new LightbringerRemoveAbility(), new LightbringerHealAbility());
    }

    @Override
    public String getName() {
        return "Lightbringer";
    }

    @Override
    public String getDescription() {
        return "Lucifer's sword";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getIcon() {
        return "lightbringer_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public Element getElement() {
        return Element.Light;
    }

    @Override
    public boolean isForbiddenToEquip(Tower tower) {
        if (Sim.context().version < Sim.v20) {
            return false;
        }
        return tower.getType() != TowerType.Lucifer;
    }

    @Override
    public boolean isAllowedToReturnToInventory() {
        return Sim.context().version < Sim.v20;
    }
}
