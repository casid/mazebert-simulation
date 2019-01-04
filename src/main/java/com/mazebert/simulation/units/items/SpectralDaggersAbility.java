package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class SpectralDaggersAbility extends Ability<Tower> implements OnAttackListener {
    public static final float chance = 0.8f;
    public static final float critChance = 0.2f;

    public static final int maxStacksBase = 4;
    public static final int maxStacksSetBonus = 3;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private int stacks;
    private int maxStacks = maxStacksBase;
    private Creep currentTarget;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
        unit.addCritChance(critChance);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        unit.addCritChance(-critChance);

        removeStacks();
        currentTarget = null;

        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        if (currentTarget != target) {
            currentTarget = target;

            if (stacks > 0) {
                removeStacks();

                if (simulationListeners.areNotificationsEnabled()) {
                    simulationListeners.showNotification(getUnit(), "Focus lost!", 0x0b8301);
                }
            }
        }

        if (getUnit().isAbilityTriggered(chance) && stacks < maxStacks) {
            addStack();
        }
    }

    public void setMaxStacks(int maxStacks) {
        this.maxStacks = maxStacks;
    }

    private void addStack() {
        ++stacks;
        getUnit().addMulticrit(1);
    }

    private void removeStacks() {
        getUnit().addMulticrit(-stacks);
        stacks = 0;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Focus";
    }

    @Override
    public String getDescription() {
        return format.percent(chance) + "% chance on attack to gain +1 multicrit. Can stack " + maxStacksBase + " times. The stack is lost upon changing target.";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(critChance) + " crit chance";
    }
}
