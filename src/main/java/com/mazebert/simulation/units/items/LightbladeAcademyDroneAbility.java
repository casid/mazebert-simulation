package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class LightbladeAcademyDroneAbility extends ProjectileDamageAbility {
    public static final float CHANCE = 0.05f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public LightbladeAcademyDroneAbility() {
        super(ProjectileViewType.LaserBeam, 20);
    }

    @Override
    public void onAttack(Creep target) {
        if (getUnit().isAbilityTriggered(CHANCE)) {
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.soundNotification("sounds/gt1-shot.mp3");
            }
            super.onAttack(target);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Sidekick";
    }

    @Override
    public String getDescription() {
        return "Whenever the carrier attacks, there is a " +
                format.percent(CHANCE) + "% chance GT1 shoots a laser beam at the target, dealing 100% of the carrier's damage.";
    }
}
