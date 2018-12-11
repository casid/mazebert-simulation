package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.abilities.AttackAbility;

public class MuliAttack extends AttackAbility {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

    private int bananas;
    private MuliState state = MuliState.Normal;
    private float hangoverDuration;

    @Override
    protected boolean onCooldownReached() {
        if (!hasTargetToAttack()) {
            tryToDrinkLiquor();
            return true;
        }

        if (state == MuliState.Normal && bananas > 0) {
            if (super.onCooldownReached()) {
                --bananas;
                return true;
            }
        } else if (state == MuliState.Drunk) {
            simulationListeners.showNotification(getUnit(), "Hangover!", 0xbaa759);
            state = MuliState.Hangover;

            hangoverDuration = getUnit().getCooldown(MuliBooze.HANGOVER_DURATION);
            return true;
        }

        return false;
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);

        if (state == MuliState.Hangover) {
            hangoverDuration -= dt;
            if (hangoverDuration <= 0) {
                simulationListeners.showNotification(getUnit(), "Recovered!", 0xbaa759);
                state = MuliState.Normal;
            }
        }
    }

    private void tryToDrinkLiquor() {
        Tower tower = getUnit();
        if (tower.isAbilityTriggered(MuliBooze.CHANCE) && tower.getWizard().gold >= MuliBooze.GOLD) {
            tower.addCritChance(MuliBooze.CRIT_CHANCE_ADD);
            tower.addCritDamage(MuliBooze.CRIT_DAMAGE_ADD);
            tower.getWizard().addGold(-MuliBooze.GOLD);

            simulationListeners.showNotification(tower, "Liquor bought", 0xffff00);
            state = MuliState.Drunk;
        }
    }

    public int getBananas() {
        return bananas;
    }

    public void addBananas(int amount) {
        bananas += amount;
    }

    public MuliState getState() {
        return state;
    }
}
