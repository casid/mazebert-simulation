package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DagonTest extends SimTest {
    Wizard wizard;
    Dagon dagon;
    Tower tower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        waveGateway = new WaveGateway();
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new TestMap(1);
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        dagon = new Dagon();
        dagon.setWizard(wizard);
        unitGateway.addUnit(dagon);

        tower = new TestTower();
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);
    }

    @Test
    void bonuses() {
        assertThat(tower.getItemChance()).isEqualTo(1.2f);
        assertThat(waveGateway.getCultistChance()).isEqualTo(1.1f);
        assertThat(tower.getEldritchCardModifier()).isEqualTo(2.0f);
    }

    @Test
    void itemStatsAreDoubled_Clam() {
        whenItemIsEquipped(tower, ItemType.EldritchClam);
        assertThat(tower.getItemChance()).isEqualTo(1.6f);
        assertThat(tower.getGoldModifier()).isEqualTo(1.4f);
        assertThat(tower.getLuck()).isEqualTo(0.6f);
    }

    @Test
    void itemStatsAreDoubled_Arms() {
        AttackAbility attackAbility = new AttackAbility();
        tower.addAbility(attackAbility);

        whenItemIsEquipped(tower, ItemType.EldritchArms);

        assertThat(attackAbility.getTargets()).isEqualTo(17);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(-16.0f);
        assertThat(tower.getLuck()).isEqualTo(-1.0f);
    }
}