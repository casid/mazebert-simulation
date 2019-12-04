package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.Game;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnDamageListener;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class TimeLordEffect extends Ability<Creep> implements OnDamageListener, OnUpdateListener {

    public static final float GRANT_INTERVAL = 1.0f;

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private double totalDamage;
    private int lastGrantedSeconds;
    private int lastTimeLordSeconds;
    private double timePassed;

    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.onDamage.add(this);
        unit.onUpdate.add(this);

        unit.setImmortal(true);
        unit.setSteady(true);
        unit.setBaseSpeed(0.25f);

        lastGrantedSeconds = gameGateway.getGame().bonusRoundSeconds;
    }

    @Override
    protected void dispose(Creep unit) {
        unit.onDamage.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (damage > 0) {
            totalDamage += damage;
        }
    }

    @Override
    public void onUpdate(float dt) {
        timePassed += dt;
        if (timePassed >= GRANT_INTERVAL) {
            timePassed -= GRANT_INTERVAL;
            grantBonusRoundSecondsAndExperience();
        }
    }

    private void grantBonusRoundSecondsAndExperience() {
        Game game = gameGateway.getGame();

        int timeLordSeconds = (int) StrictMath.round(0.02 * StrictMath.sqrt(totalDamage));
        int totalSeconds = timeLordSeconds - lastTimeLordSeconds + game.bonusRoundSeconds;

        int deltaSeconds = totalSeconds - lastGrantedSeconds;
        int grantedSeconds = 0;
        while (deltaSeconds >= ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL) {
            deltaSeconds -= ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL;
            grantedSeconds += ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL;

            int bonusRoundSeconds = game.bonusRoundSeconds + grantedSeconds;
            unitGateway.forEach(Wizard.class, wizard -> experienceSystem.grantBonusRoundExperience(wizard, bonusRoundSeconds, false));
        }

        if (grantedSeconds > 0) {
            game.bonusRoundSeconds += grantedSeconds;
            simulationListeners.onBonusRoundSurvived.dispatch(game.bonusRoundSeconds);

            lastTimeLordSeconds = timeLordSeconds;
            lastGrantedSeconds = game.bonusRoundSeconds;

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "+" + format.seconds(grantedSeconds), 0xffff00);
            }
        }
    }
}
