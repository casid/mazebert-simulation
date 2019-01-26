package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.BranchOfYggdrasil;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.WoodenStaff;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class YggdrasilTest extends SimTest {
    Wizard wizard;
    Yggdrasil yggdrasil;


    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        lootSystem = new LootSystemTrainer();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard = new Wizard();
        wizard.gold = 10000;
        unitGateway.addUnit(wizard);

        yggdrasil = new Yggdrasil();
        yggdrasil.setWizard(wizard);
    }

    @Test
    void containsBranchesWhenBuilt() {
        whenYggdrasilIsBuilt();
        thenInventoryIsFilledWithBranches();
    }

    @Test
    void existingItemsAreReplacedWithBranches() {
        yggdrasil.setItem(0, new WoodenStaff());

        whenYggdrasilIsBuilt();

        thenInventoryIsFilledWithBranches();
        assertThat(wizard.itemStash.get(ItemType.WoodenStaff).amount).isEqualTo(1);
    }

    @Test
    void potionsAreAddedToBranchCarriers() {
        whenYggdrasilIsBuilt();
        whenYggdrasilDrinksPotion();

        assertThat(yggdrasil.getAttackSpeedAdd()).isEqualTo(0.04f + 4 * 0.04f);
    }

    @Test
    void otherBranchCarrier() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Nature);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);

        whenYggdrasilDrinksPotion();

        assertThat(yggdrasil.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void otherBranchCarrier_onlyOneBranchPerTower() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Nature);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);
        whenBranchIsEquipped(tower, 1);

        assertThat(tower.getItem(0)).isInstanceOf(BranchOfYggdrasil.class);
        assertThat(tower.getItem(1)).isNull();
    }

    @Test
    void otherBranchCarrier_onlyNatureTowers() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Metropolis);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);

        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void otherBranchCarrier_removedWhenReplacedByNonNatureTower() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Nature);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);

        whenTowerIsReplaced(tower, TowerType.Hitman);

        Hitman hitman = unitGateway.findUnit(Hitman.class, wizard.playerId);
        assertThat(hitman.getItem(0)).isNull();
        assertThat(wizard.itemStash.get(ItemType.BranchOfYggdrasil).amount).isEqualTo(4);
    }

    private void whenYggdrasilIsBuilt() {
        unitGateway.addUnit(yggdrasil);
    }

    private void whenYggdrasilDrinksPotion() {
        wizard.potionStash.add(PotionType.CommonSpeed);
        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = PotionType.CommonSpeed;
        commandExecutor.executeVoid(command);
    }

    private void whenBranchIsEquipped(Tower tower, int index) {
        EquipItemCommand command = new EquipItemCommand();
        command.inventoryIndex = index;
        command.towerX = (int) tower.getX();
        command.towerY = (int) tower.getY();
        command.itemType = ItemType.BranchOfYggdrasil;
        commandExecutor.executeVoid(command);
    }

    @SuppressWarnings("SameParameterValue")
    private void whenTowerIsReplaced(Tower tower, TowerType type) {
        wizard.towerStash.add(type);
        BuildTowerCommand command = new BuildTowerCommand();
        command.x = (int) tower.getX();
        command.y = (int) tower.getY();
        command.towerType = type;
        command.playerId = wizard.playerId;
        commandExecutor.executeVoid(command);
    }

    private void thenInventoryIsFilledWithBranches() {
        for (int i = 0; i < yggdrasil.getInventorySize(); ++i) {
            assertThat(yggdrasil.getItem(0)).isInstanceOf(BranchOfYggdrasil.class);
        }
    }
}