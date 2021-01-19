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
        if (timeLordSeconds > 2_000_000_000 || timeLordSeconds < 0) {
            // Integer caps at 2.147.483.647, that's an insane amount for bonus round time!!!
            // We stop time lord bonus round seconds increment if 2 billions are exceeded.
            timeLordSeconds = 2_000_000_000;
        }

        int totalSeconds = timeLordSeconds - lastTimeLordSeconds + game.bonusRoundSeconds;

        int grantedSeconds = grantExperience(game, totalSeconds);
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

    private int grantExperience(Game game, int totalSeconds) {
        int deltaSeconds = totalSeconds - lastGrantedSeconds;
        int grantedSeconds = 0;

        if (Sim.context().version >= Sim.v23) {
            int amount = deltaSeconds / ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL;
            if (amount > 0) {
                unitGateway.forEach(Wizard.class, wizard -> experienceSystem.grantTimeLordExperience(wizard, game.bonusRoundSeconds, amount));
                grantedSeconds = amount * ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL;
            }
        } else {
            // This has a huge performance impact for very high scores.
            while (deltaSeconds >= ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL) {
                deltaSeconds -= ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL;
                grantedSeconds += ExperienceSystem.BONUS_ROUND_REWARD_INTERVAL;

                int bonusRoundSeconds = game.bonusRoundSeconds + grantedSeconds;
                unitGateway.forEach(Wizard.class, wizard -> experienceSystem.grantBonusRoundExperience(wizard, bonusRoundSeconds, false));
            }
        }

        return grantedSeconds;
    }
}
