package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.listeners.OnWaveFinishedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.wizards.Wizard;

public abstract strictfp class KillCultistsQuest extends Quest implements OnUnitRemovedListener, OnWaveFinishedListener {

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
        simulationListeners.onWaveFinished.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        simulationListeners.onWaveFinished.remove(this);
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
    public void onWaveFinished(Wave wave) {
        if (wave.type == type) {
            if (Sim.context().simulationListeners.areNotificationsEnabled()) {
                Sim.context().simulationListeners.showNotification(getUnit(), format.card(getHeroToUnlock()) + format.colored(" is rising...", 0x038174));
                Sim.context().simulationListeners.showNotification(getUnit(), format.colored(getCurrentAmount() + "/" + getRequiredAmount() + " cultists slain.", 0x038174));
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
        return "Sacrifice for " + format.card(getHeroToUnlock());
    }

    @Override
    public String getDescription() {
        return "Slay " + requiredAmount + " " + format.waveTypePlural(type);
    }
}
