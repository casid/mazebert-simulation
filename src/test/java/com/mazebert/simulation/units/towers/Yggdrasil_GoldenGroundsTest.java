package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.GoldenGrounds;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Yggdrasil_GoldenGroundsTest extends SimTest {

    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();

        lootSystem = new LootSystem();

        gameGateway.getGame().map = new GoldenGrounds();

        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard = new Wizard();
        wizard.addGold(100000);
        wizard.foilTowers.add(TowerType.Yggdrasil);
        unitGateway.addUnit(wizard);
    }

    @Test
    void nonGoldenBranches() {
        Tower yggdrasil = whenTowerIsBuilt(wizard, TowerType.Yggdrasil, 13, 8);

        whenItemIsEquipped(yggdrasil, null, 0);

        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BranchOfYggdrasilNature);
    }
}