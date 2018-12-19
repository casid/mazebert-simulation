package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnWaveFinishedListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.usecases.BuildTower;

public strictfp class KiwiEggHatch extends Ability<KiwiEgg> implements OnUnitAddedListener, OnWaveFinishedListener {
    public static final int ROUNDS = 5;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

    private int rounds;

    @Override
    protected void initialize(KiwiEgg unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(KiwiEgg unit) {
        unit.onUnitAdded.remove(this);
        simulationListeners.onWaveFinished.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        simulationListeners.onWaveFinished.add(this);
    }

    @Override
    public void onWaveFinished(Wave wave) {
        if (++rounds >= ROUNDS) {
            hatch();
        }
    }

    private void hatch() {
        Kiwi kiwi = new Kiwi();
        if (randomPlugin.getFloatAbs() < 0.5f) {
            kiwi.setGender(Gender.Female);
        } else {
            kiwi.setGender(Gender.Male);
        }

        BuildTower buildTower = new BuildTower();
        buildTower.summonTower(kiwi, getUnit().getWizard(), (int) getUnit().getX(), (int) getUnit().getY());

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(kiwi, "Kiwi hatched", 0x9e6120);
        }
    }

    public int getRounds() {
        return rounds;
    }
}
