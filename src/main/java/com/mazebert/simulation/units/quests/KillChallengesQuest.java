package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class KillChallengesQuest extends Quest implements OnUnitRemovedListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public KillChallengesQuest() {
        super(QuestReward.Medium, 7);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        simulationListeners.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        simulationListeners.onUnitRemoved.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Creep) {
            Creep creep = (Creep)unit;
            if (creep.getWizard() != null && creep.getWizard() != getUnit()) {
                return;
            }
            if (!creep.isDead()) {
                return;
            }

            if (creep.getWave().type == WaveType.Challenge) {
                addAmount(1);
            }
        }
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Headhunter";
    }

    @Override
    public String getDescription() {
        return "Kill " + requiredAmount + format.colored(" Challenges", 0xffff00) + "!";
    }
}
