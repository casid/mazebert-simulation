package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class YigAbility extends Ability<Hero> implements OnUnitRemovedListener, OnUnitAddedListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final WaveGateway waveGateway = Sim.context().waveGateway;

    private int kills;

    @Override
    protected void initialize(Hero unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(u -> buffPlayerAndStartListening());
    }

    private void buffPlayerAndStartListening() {
        // No need to clean up, as the hero lasts the entire game
        simulationListeners.onUnitRemoved.add(this);
        simulationListeners.onUnitAdded.add(this);

        getUnit().getWizard().addHealth(1.0f);
        waveGateway.addCultistOfYigHealthMultiplier(1.0f);
    }


    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Creep) {
            Creep creep = (Creep)unit;
            if (creep.getWave().type == WaveType.CultistOfYig) {
                ++kills;
            }
        }
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit instanceof Creep) {
            Creep creep = (Creep)unit;
            creep.addArmor(-kills);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Feeding Frenzy";
    }

    @Override
    public String getDescription() {
        return "+100% player health.\n" +
                format.waveTypePlural(WaveType.CultistOfYig) + " have 100% more health.\n" +
                "-1 creep armor per slain Cultist of Yig.";
    }
}
