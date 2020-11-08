package com.mazebert.simulation.units.abilities;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.minigames.BowlingGame;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.BowlingBallUnit;
import com.mazebert.simulation.units.quests.BowlPerfectGameQuest;
import com.mazebert.simulation.units.quests.BowlStrikesQuest;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.HashSet;
import java.util.Set;

import static com.mazebert.simulation.units.items.BowlingBall.DEATH_CHANCE;
import static com.mazebert.simulation.units.items.BowlingBall.NOTIFICATION_COLOR;

public strictfp class FollowPathBowlingBallAbility extends FollowPathAbility<BowlingBallUnit> implements Consumer<Creep> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    private Set<Creep> creepsHit = new HashSet<>();
    private int rolledOverCount;

    @Override
    protected void dispose(BowlingBallUnit unit) {
        creepsHit.clear();
        creepsHit = null;
        super.dispose(unit);
    }

    @Override
    protected float getSpeed() {
        return 3.0f;
    }

    @Override
    protected void onTargetReached() {
        BowlingGame game = getUnit().getGame();
        game.roll(rolledOverCount);

        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.soundNotification("sounds/bowling-roll-complete.mp3");
        }

        if (game.isStrike(game.getLastRollIndex())) {
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Strike!", NOTIFICATION_COLOR);
            }
            getUnit().getWizard().addQuestProgress(BowlStrikesQuest.class);
        } else if (game.isSpare(game.getLastRollIndex())) {
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Spare!", NOTIFICATION_COLOR);
            }
        }

        if (game.isFinished()) {
            int score = game.getScore();
            Wizard wizard = getUnit().getWizard();

            long experience = experienceSystem.calculateExperience(wizard, score);
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(wizard, "Bowling game score: " + score + "(+ " + experience + " XP)");
            }
            experienceSystem.grantExperience(wizard, experience);

            if (game.getScore() >= 300) {
                if (simulationListeners.areNotificationsEnabled()) {
                    simulationListeners.showNotification(wizard, "This is a perfect game!");
                }
                wizard.addQuestProgress(BowlPerfectGameQuest.class);
            }
        }

        unitGateway.removeUnit(getUnit());
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        if (!isDisposed()) {
            unitGateway.forEachInRange(getUnit().getX(), getUnit().getY(), 1, Creep.class, this);
        }
    }

    @Override
    public void accept(Creep creep) {
        if (isDisposed()) {
            return;
        }

        if (creep.isBoss()) {
            onTargetReached();
        } else if (!creep.isAir() && !creepsHit.contains(creep)) {
            creepsHit.add(creep);

            if (getUnit().getTower().isAbilityTriggered(DEATH_CHANCE)) {
                ++rolledOverCount;
                getUnit().getTower().kill(creep);
            }
        }
    }
}
