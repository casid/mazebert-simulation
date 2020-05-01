package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class SacrificeAbility extends Ability<Tower> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(unit, "Tower sacrificed!", 0xff0000);
        }

        unit.getWizard().addHealth(0.01f * unit.getLevel() * unit.getPotionEffectiveness());
        Sim.context().unitGateway.destroyTower(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Self-sacrifice";
    }

    @Override
    public String getDescription() {
        return "When a tower drinks this potion, it dies instantly. The flask's dark powers use the tower's soul to restore your health.";
    }

    @Override
    public String getLevelBonus() {
        return "+1% health restored per level.";
    }
}
