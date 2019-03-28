package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class SkullOfDarknessAbility extends Ability<Tower> implements OnKillListener {

    private static final int maxHealings = 100;
    private static final float healthPerKill = 0.01f;
    private static final float chance = 0.1f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private int remainingHealings = maxHealings;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onKill.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onKill.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onKill(Creep target) {
        if (getUnit().isAbilityTriggered(chance)) {
            heal();
        }
    }

    private void heal() {
        Tower tower = getUnit();
        tower.getWizard().addHealth(healthPerKill);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(tower, "Skull healed you!", 0x333333);
        }

        if (--remainingHealings <= 0) {
            tower.removeItem(ItemType.SkullOfDarkness);
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(tower, "Skull destroyed!", 0x333333);
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Blood Steal";
    }

    @Override
    public String getDescription() {
        return format.percent(chance) + "% chance to gain " + format.percent(healthPerKill) + "% health whenever the carrier kills a creep. This item is destroyed after " + maxHealings + " healings.";
    }

    @Override
    public String getLevelBonus() {
        return "Remaining healings: " + remainingHealings;
    }
}
