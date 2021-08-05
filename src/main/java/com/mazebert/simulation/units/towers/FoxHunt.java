package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.PermanentAbilitySystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class FoxHunt extends Ability<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnRoundStartedListener {

    private static final float CHANCE = 0.1f;
    private static final float CRIT_CHANCE_PER_RABBIT = 0.02f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    private int rabbitsEaten;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
        unit.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        unit.onUnitRemoved.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        simulationListeners.onRoundStarted.add(this);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        simulationListeners.onRoundStarted.remove(this);
    }

    @Override
    public void onRoundStarted(Wave wave) {
        if (getUnit().isAbilityTriggered(CHANCE)) {
            huntRabbit();
        }
    }

    private void huntRabbit() {
        Rabbit rabbit = unitGateway.findUnit(Rabbit.class, r -> r.getWizard() == getUnit().getWizard());
        if (rabbit != null) {
            eatRabbit(rabbit);
        }
    }

    private void eatRabbit(Rabbit rabbit) {
        getUnit().addCritChance(CRIT_CHANCE_PER_RABBIT);
        experienceSystem.grantExperience(getUnit(), rabbit.getExperience(), true);

        rabbit.markForDisposal();
        PermanentAbilitySystem.transferAll(rabbit, getUnit());
        unitGateway.destroyTower(rabbit);

        ++rabbitsEaten;
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(getUnit(), "Rabbit eaten");
        }
    }

    public int getRabbitsEaten() {
        return rabbitsEaten;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Rabbit Hunter";
    }

    @Override
    public String getDescription() {
        return "Each round there is a " + format.percent(CHANCE) + "% chance " + format.card(TowerType.Fox) + " eats a " + format.card(TowerType.Rabbit) + " on the field, carrying over the Rabbit’s experience and potions.";
    }

    @Override
    public String getIconFile() {
        return "fox";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(CRIT_CHANCE_PER_RABBIT) + " crit chance per " + format.card(TowerType.Rabbit) + " eaten";
    }
}
