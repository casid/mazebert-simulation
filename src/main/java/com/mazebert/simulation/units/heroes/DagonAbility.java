package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DagonAbility extends HeroTowerBuffAbility {

    public static final float itemChance = 0.1f;
    public static final float cultistChance = 0.05f;
    public static final float eldritchCardModifier = 1.0f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addItemChance((1.0f + eldritchCardModifier) * itemChance);
        tower.addEldritchCardModifier(eldritchCardModifier);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit == getUnit()) {
            Sim.context().waveGateway.addCultistChance((1.0f + eldritchCardModifier) * cultistChance);
        }
        super.onUnitAdded(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Deep Treasures";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(itemChance) + " item chance.\n" +
                format.percentWithSignAndUnit(cultistChance) + " chance to spawn eldritch cultists.\n" +
                "Eldritch card effects are doubled.";
    }
}
