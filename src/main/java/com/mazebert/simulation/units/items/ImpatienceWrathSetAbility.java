package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnSecondsSkippedListener;

import java.util.EnumSet;

public class ImpatienceWrathSetAbility extends ItemSetAbility implements OnSecondsSkippedListener {
    public static final float bonusPerSecond = 0.3f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float currentBonus;

    public ImpatienceWrathSetAbility() {
        super(EnumSet.of(ItemType.ImpatienceWrathWatch, ItemType.ImpatienceWrathTrain, ItemType.ImpatienceWrathForce));
    }

    @Override
    protected void updateSetBonus(EnumSet<ItemType> items, int oldAmount, int newAmount) {
        if (newAmount == 3) {
            addBonus();
            simulationListeners.onSecondsSkipped.add(this);
        }
        if (newAmount < 3 && oldAmount == 3) {
            removeBonus();
            simulationListeners.onSecondsSkipped.remove(this);
        }
    }

    @Override
    public void onSecondsSkipped() {
        removeBonus();
        addBonus();
    }

    private void addBonus() {
        currentBonus = Sim.context().skippedSeconds * bonusPerSecond;
        getUnit().addAttackSpeed(currentBonus);
    }

    private void removeBonus() {
        if (currentBonus > 0.0f) {
            getUnit().addAttackSpeed(-currentBonus);
            currentBonus = 0.0f;
        }
    }

    @Override
    public String getTitle() {
        return "Impatience's Wrath";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonusPerSecond) + " attack speed for each second skipped by the next wave button.\n(3 set items)";
    }
}
