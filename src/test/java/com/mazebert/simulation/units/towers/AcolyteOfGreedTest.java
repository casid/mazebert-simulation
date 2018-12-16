package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class AcolyteOfGreedTest extends SimTest {
    Wizard wizard;
    Tower previousTower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = new RandomPluginTrainer();

        waveGateway = new WaveGateway();
        waveGateway.setCurrentRound(50);

        gameGateway = new GameGateway();
        gameGateway.getGame().map = new BloodMoor();

        difficultyGateway = new DifficultyGateway();
        waveSpawner = new WaveSpawner();

        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        previousTower = new TestTower();
        previousTower.setWizard(wizard);
        unitGateway.addUnit(previousTower);
    }

    @Test
    void replaceTower_level1() {
        previousTower.setExperience(Balancing.getTowerExperienceForLevel(1));

        whenAcolyteIsBuilt(0, 0);
        waveSpawner.onUpdate(1.0f);

        Creep goblin = unitGateway.findUnit(Creep.class, wizard.getPlayerId());
        assertThat(goblin).isNotNull();

        Wave wave = goblin.getWave();
        assertThat(wave.origin).isEqualTo(WaveOrigin.Treasure);
        assertThat(wave.round).isEqualTo(50);
        assertThat(wave.type).isEqualTo(WaveType.Normal);
        assertThat(wave.creepType).isEqualTo(CreepType.Gnome);
        assertThat(wave.armorType).isEqualTo(ArmorType.Zod);

        assertThat(goblin.getWizard()).isSameAs(wizard);
        assertThat(goblin.getType()).isEqualTo(wave.creepType);
        assertThat(goblin.getDropChance()).isEqualTo(4.0f);
        assertThat(goblin.getMaxItemLevel()).isEqualTo(50);
        assertThat(goblin.getMinDrops()).isEqualTo(1);
        assertThat(goblin.getMaxDrops()).isEqualTo(4);
        assertThat(goblin.getGold()).isEqualTo(93);
        assertThat(goblin.getArmor()).isEqualTo(100);
        assertThat(goblin.getHealth()).isEqualTo(8123.0);
        assertThat(goblin.getMaxHealth()).isEqualTo(goblin.getHealth());
    }

    @Test
    void replaceTower_level80() {
        previousTower.setExperience(Balancing.getTowerExperienceForLevel(80));

        whenAcolyteIsBuilt(0, 0);

        waveSpawner.onUpdate(1.0f);
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);
        waveSpawner.onUpdate(1.0f);
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(2);
        waveSpawner.onUpdate(1.0f);
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(3);
    }

    @Test
    void buildTower() {
        whenAcolyteIsBuilt(1, 2);
        waveSpawner.onUpdate(1.0f);
        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1);
    }

    private void whenAcolyteIsBuilt(int x, int y) {
        wizard.towerStash.add(TowerType.AcolyteOfGreed);

        BuildTowerCommand command = new BuildTowerCommand();
        command.towerType = TowerType.AcolyteOfGreed;
        command.playerId = wizard.playerId;
        command.x = x;
        command.y = y;
        commandExecutor.executeVoid(command);
    }
}