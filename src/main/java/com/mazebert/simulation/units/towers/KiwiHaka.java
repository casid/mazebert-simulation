package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.abilities.ActiveAbility;

public class KiwiHaka extends ActiveAbility {

    public static final float COOLDOWN = 300.0f;
    public static final float DURATION = 16.0f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private float remainingDuration;
    private KiwiHakaAura aura;

    @Override
    public float getCooldown() {
        float attackSpeedModifier = getUnit().getAttackSpeedModifier();
        if (attackSpeedModifier <= 0.0) {
            return COOLDOWN;
        }

        float cooldown = COOLDOWN / attackSpeedModifier;
        if (cooldown < 2 * DURATION) {
            cooldown = 2 * DURATION;
        }
        return cooldown;
    }

    @Override
    public void activate() {
        start();
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);

        if (remainingDuration > 0) {
            remainingDuration -= dt;
            if (remainingDuration <= 0) {
                end();
            }
        }
    }

    private void start() {
        remainingDuration = DURATION;
        aura = new KiwiHakaAura(this);
        getUnit().addAbility(aura);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.soundNotification("sounds/haka.mp3");
            simulationListeners.showNotification(getUnit(), "Haka!", 0xffab00);
        }

        startCooldown();
    }

    private void end() {
        remainingDuration = 0;
        getUnit().removeAbility(aura);
        aura = null;
    }

    public float getRemainingDuration() {
        return remainingDuration;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Haka";
    }

    @Override
    public String getDescription() {
        return "Kiwi performs the mighty Haka stunning all creeps in range for " + format.seconds(DURATION) + ".";
    }

    @Override
    public String getIconFile() {
        return "kiwi_haka_512";
    }

    @Override
    public String getLevelBonus() {
        return "Cooldown " + format.seconds(COOLDOWN);
    }
}
