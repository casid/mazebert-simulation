package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.listeners.OnGameWonListener;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.units.wizards.Wizard;

public abstract strictfp class MapVictoryQuest extends Quest implements OnGameWonListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final GameGateway gameGateway = Sim.context().gameGateway;

    private final MapType mapType;

    public MapVictoryQuest(MapType mapType) {
        this(mapType, QuestReward.Huge);
    }

    public MapVictoryQuest(MapType mapType, QuestReward reward) {
        super(reward, 1);
        this.mapType = mapType;
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        if (gameGateway.getMap().getType() == mapType) {
            simulationListeners.onGameWon.add(this);
        }
    }

    @Override
    protected void dispose(Wizard unit) {
        simulationListeners.onGameWon.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onGameWon() {
        addAmount(1);
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
