package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.BranchOfYggdrasilLegacy;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.WoodenStaff;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class Yggdrasil_v19Test extends SimTest {
    Wizard wizard;
    Yggdrasil yggdrasil;


    @BeforeEach
    void setUp() {
        version = Sim.v19;

        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        lootSystem = new LootSystemTrainer();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        gameGateway.getGame().map = new TestMap(2);

        wizard = new Wizard();
        wizard.gold = 10000;
        unitGateway.addUnit(wizard);

        yggdrasil = new Yggdrasil();
        yggdrasil.setWizard(wizard);
    }

    @Test
    void element() {
        assertThat(yggdrasil.getElement()).isEqualTo(Element.Nature);
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
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);

        whenYggdrasilDrinksPotion();

        assertThat(yggdrasil.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void otherBranchCarrier_tears() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Nature);
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);

        whenYggdrasilDrinksPotion(PotionType.Tears);

        assertThat(yggdrasil.getMulticrit()).isEqualTo(2);
        assertThat(tower.getMulticrit()).isEqualTo(2);
    }

    @Test
    void otherBranchCarrier_onlyOneBranchPerTower() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Nature);
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);
        whenBranchIsEquipped(tower, 1);

        assertThat(tower.getItem(0)).isInstanceOf(BranchOfYggdrasilLegacy.class);
        assertThat(tower.getItem(1)).isNull();
    }

    @Test
    void otherBranchCarrier_onlyNatureTowers() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Metropolis);
        tower.setWizard(wizard);
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
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenBranchIsEquipped(tower, 0);

        whenTowerIsReplaced(tower, TowerType.Hitman);

        Hitman hitman = unitGateway.findUnit(Hitman.class, wizard.playerId);
        assertThat(hitman.getItem(0)).isNull();
        assertThat(wizard.itemStash.get(ItemType.BranchOfYggdrasilLegacy).amount).isEqualTo(4);
    }

    private void whenYggdrasilIsBuilt() {
        unitGateway.addUnit(yggdrasil);
    }

    private void whenYggdrasilDrinksPotion() {
        whenYggdrasilDrinksPotion(PotionType.CommonSpeed);
    }

    private void whenYggdrasilDrinksPotion(PotionType type) {
        whenPotionIsConsumed(yggdrasil, type);
    }

    private void whenBranchIsEquipped(Tower tower, int index) {
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilLegacy, index);
    }

    private void thenInventoryIsFilledWithBranches() {
        for (int i = 0; i < yggdrasil.getInventorySize(); ++i) {
            assertThat(yggdrasil.getItem(0)).isInstanceOf(BranchOfYggdrasilLegacy.class);
        }
    }
}