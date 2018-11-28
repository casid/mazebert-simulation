package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public class SacrificeAbility extends Ability<Tower> {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        // TODO notification?
        //Mazebert.instance.hud.showTowerNotification(tower, "Tower sacrificed!", 0xff0000);

        Sim.context().gameGateway.getGame().health += 0.01f * getUnit().getLevel();
        Sim.context().unitGateway.destroyTower(getUnit());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Sacrifice";
    }

    @Override
    public String getDescription() {
        return "When your tower drinks this potion, it dies instantly. The dark powers of the flask tear down the tower's soul and restore some amount of your health.";
    }

    @Override
    public String getLevelBonus() {
        return "+ 1% health restored per tower level";
    }
}
