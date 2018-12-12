package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class ScareCrowMultishot extends AttackAbility implements OnLevelChangedListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private boolean crowsNeedToReturn;

    public ScareCrowMultishot() {
        super(1, true);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onLevelChanged.add(this);

        updateCrows();
    }

    @Override
    public void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    protected boolean onCooldownReached() {
        if (crowsNeedToReturn) {
            crowsNeedToReturn = false;
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Crows returned", 0x222222);
            }
            return true;
        }

        if (super.onCooldownReached()) {
            crowsNeedToReturn = true;
            return true;
        }

        return false;
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        updateCrows();
    }

    private void updateCrows() {
        setTargets(2 + getUnit().getLevel() / 14);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Dark wings";
    }

    @Override
    public String getDescription() {
        return "Scarecrow attacks with all crows sitting on it. Every 2nd attack the crows return. Scarecrow commands 2 crows.";
    }

    @Override
    public String getIconFile() {
        return "plume_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 1 crow every 14 levels";
    }
}
