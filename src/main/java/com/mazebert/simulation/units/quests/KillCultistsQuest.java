package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.wizards.Wizard;

public abstract strictfp class KillCultistsQuest extends Quest implements OnUnitRemovedListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private final WaveType type;

    public KillCultistsQuest(WaveType type) {
        super(QuestReward.Huge, 5000);
        this.type = type;
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
            if (!creep.isDead()) {
                return;
            }

            if (creep.getWave().type == type) {
                addAmount(1);
            }
        }
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getSinceVersion() {
        return "2.1.0";
    }

    protected abstract HeroType getHeroToUnlock();

    @Override
    public String getTitle() {
        return "Offerings for " + format.card(getHeroToUnlock());
    }

    @Override
    public String getDescription() {
        return "Kill " + requiredAmount + " " + format.waveTypePlural(type);
    }
}
