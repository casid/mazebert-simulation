package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnDeathListener;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class AbyssKingSwallowEffect extends StackableByOriginAbility<Creep> implements OnDeathListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onDeath.add(this);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onDeath.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onDeath(Creep creep) {
        AbyssKing abyssKing = (AbyssKing)getOrigin();
        float chance = AbyssKingSwallow.chance + abyssKing.getLevel() * AbyssKingSwallow.chancePerLevel;
        if (abyssKing.isAbilityTriggered(chance)) {
            abyssKing.addCreepToArmy();

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(abyssKing, "Undead Reinforcement", 0x444444);
            }
        }
    }
}
