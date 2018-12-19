package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.creeps.Creep;

public class TheRipperKillingSpree extends CooldownAbility<Tower> implements OnKillListener {
    private static final float BONUS_PER_KILL = 1.0f;
    private static final float BONUS_PER_LEVEL = 0.02f;
    private static final float BONUS_TIME = 2.0f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private boolean isActive;
    private boolean isReplenishing;
    private float addedBonus;

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
    protected float getCooldown() {
        return BONUS_TIME;
    }

    @Override
    protected boolean onCooldownReached() {
        if (isActive) {
            if (isReplenishing) {
                removeBonus();
            } else {
                isReplenishing = true;
            }

            return isReplenishing;
        } else {
            return false;
        }
    }

    @Override
    public void onKill(Creep target) {
        if (!isActive) {
            addBonus();
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.soundNotification("sounds/slash-killing-spree.mp3");
                simulationListeners.showNotification(getUnit(), "Killing Spree!", 0xff0000);
            }
        }
    }

    private void addBonus() {
        addedBonus = BONUS_PER_KILL + getUnit().getLevel() * BONUS_PER_LEVEL;
        getUnit().addAttackSpeed(addedBonus);

        isActive = true;
        isReplenishing = false;
    }

    private void removeBonus() {
        isReplenishing = false;
        isActive = false;

        getUnit().addAttackSpeed(-addedBonus);
        addedBonus = 0.0f;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Killing Spree!";
    }

    @Override
    public String getDescription() {
        return "Each kill The Ripper goes into a murderous spree gaining " + format.percent(BONUS_PER_KILL) +
                "% attack speed for " + format.seconds(BONUS_TIME) + " (does not stack).";
    }

    @Override
    public String getIconFile() {
        return "0053_charge_512";
    }
}
