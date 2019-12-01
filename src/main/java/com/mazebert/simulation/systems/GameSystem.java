package com.mazebert.simulation.systems;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.PlayerGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnWizardHealthChangedListener;
import com.mazebert.simulation.listeners.OnWaveFinishedListener;
import com.mazebert.simulation.tutorial.Tutorial;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class GameSystem implements OnWizardHealthChangedListener, OnWaveFinishedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final PlayerGateway playerGateway = Sim.context().playerGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final LootSystem lootSystem = Sim.context().lootSystem;

    private final int version = Sim.context().version;

    private Tutorial tutorial;

    public void addWizards() {
        for (int playerId = 1; playerId <= playerGateway.getPlayerCount(); ++playerId) {
            addWizard(playerId);
        }
    }

    public Wizard addWizard(int playerId) {
        Wizard wizard = new Wizard();
        wizard.playerId = playerId;
        addWizard(wizard);

        return wizard;
    }

    public void addWizard(Wizard wizard) {
        wizard.addGold(Balancing.STARTING_GOLD);

        wizard.onHealthChanged.add(this);

        unitGateway.addUnit(wizard);
    }

    public void rollStartingTowers(Wizard wizard) {
        if (isTutorial()) {
            wizard.towerStash.add(TowerType.Beaver);
            wizard.towerStash.add(TowerType.Rabbit);
            wizard.towerStash.add(TowerType.Hitman);
            wizard.towerStash.add(TowerType.Dandelion);
        } else {
            // Research starting towers (first 3 must be guaranteed to be affordable by the player)
            for (int i = 0; i < 3; ++i) {
                lootSystem.researchStartingTower(wizard);
            }

            // The last one can be anything possible for this round!
            lootSystem.researchTower(wizard, 1);
        }
    }

    @Override
    public void onHealthChanged(Unit unit, double oldHealth, double newHealth) {
        double delta = (newHealth - oldHealth) / playerGateway.getPlayerCount();
        addGameHealth((float) delta);
    }

    private void addGameHealth(float delta) {
        Game game = gameGateway.getGame();
        if (game.isLost()) {
            return;
        }

        float oldHealth = game.health;
        game.health += delta;
        if (game.health <= 0.0f) {
            game.health = 0.0f;
            if (game.bonusRound) {
                if (Sim.context().timeLordCountDown != null) {
                    Sim.context().timeLordCountDown.cancel();
                    Sim.context().timeLordCountDown = null;
                }
                finishBonusRound();
            } else {
                simulationListeners.onGameLost.dispatch();
            }
        }
        simulationListeners.onGameHealthChanged.dispatch(oldHealth, game.health);
    }

    public void finishBonusRound() {
        // Kill all remaining creeps, so that the end feels satisfiying
        unitGateway.forEachCreep(creep -> {
            creep.setImmortal(false);
            creep.setHealth(0);
        });

        if (simulationListeners.areNotificationsEnabled()) {
            unitGateway.forEach(Wizard.class, this::showBonusRoundCompleteNotification);
        }

        simulationListeners.onBonusRoundFinished.dispatch();
    }

    private void showBonusRoundCompleteNotification(Wizard wizard) {
        int survivedSeconds = gameGateway.getGame().bonusRoundSeconds;

        simulationListeners.showNotification(wizard, "Congratulations!", Float.MAX_VALUE);
        simulationListeners.showNotification(wizard, "You survived " + survivedSeconds + " seconds after a", Float.MAX_VALUE);
        simulationListeners.showNotification(wizard, Sim.context().waveGateway.getTotalWaves() + " wave game on " + Sim.context().difficultyGateway.getDifficulty() + ".", Float.MAX_VALUE);
        simulationListeners.showNotification(wizard, "Your rank: " + getBonusRoundRank(survivedSeconds), Float.MAX_VALUE);
    }

    private String getBonusRoundRank(int bonusSurvivalTime) {
        if (bonusSurvivalTime < 2 * 30) return "Novice";
        else if (bonusSurvivalTime < 2 * 60) return "Apprentice";
        else if (bonusSurvivalTime < 3 * 120) return "Scholar";
        else if (bonusSurvivalTime < 4 * 180) return "Master";
        else if (bonusSurvivalTime < 5 * 240) return "Master Defender";
        else if (bonusSurvivalTime < 6 * 300) return "Master Commander";
        else if (bonusSurvivalTime < 7 * 360) return "King's Hand";
        else if (bonusSurvivalTime < 8 * 420) return "King";
        else if (bonusSurvivalTime < 9 * 480) return "Emperor";
        else if (bonusSurvivalTime < 10 * 540) return "Master of the Universe";
        else return "Chuck Norris";
    }

    public void initTutorial() {
        if (playerGateway.getPlayerCount() == 1) {
            tutorial = new Tutorial();
        }
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public boolean isTutorial() {
        return tutorial != null;
    }

    public void endTutorial() {
        if (tutorial != null) {
            tutorial.dispose();
            tutorial = null;
        }
    }

    public void initElementResearch() {
        if (version > Sim.v10) {
            simulationListeners.onWaveFinished.add(this);
        }
    }

    @Override
    public void onWaveFinished(Wave wave) {
        if (wave.type == WaveType.Horseman) {
            unitGateway.forEach(Wizard.class, Wizard::addElementResearchPoint);
        }
    }
}
