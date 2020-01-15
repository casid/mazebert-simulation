package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.*;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class Yggdrasil_v20Test extends SimTest {
    Wizard wizard;
    Yggdrasil yggdrasil;


    @BeforeEach
    void setUp() {
        version = Sim.v20;

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
        assertThat(yggdrasil.getElement()).isEqualTo(Element.Unknown);
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
    void yggdrasilReceivesNoPotionEffects() {
        whenYggdrasilIsBuilt();
        whenYggdrasilDrinksPotion();

        assertThat(yggdrasil.getAttackSpeedAdd()).isEqualTo(0.04f);
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
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilNature, 0);

        whenYggdrasilDrinksPotion();

        assertThat(yggdrasil.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void otherBranchCarrier_tears() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Darkness);
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilDarkness, 0);

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
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilNature, 0);
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilMetropolis, 1);

        assertThat(tower.getItem(0)).isInstanceOf(BranchOfYggdrasilNature.class);
        assertThat(tower.getItem(1)).isNull();
    }

    @Test
    void otherBranchCarrier_wrongBranchElement() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Metropolis);
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilNature, 0);

        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void otherBranchCarrier_removedWhenReplacedByOtherElementTower() {
        Tower tower = new TestTower();
        tower.setX(1);
        tower.setElement(Element.Nature);
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        whenYggdrasilIsBuilt();
        unitGateway.returnAllItemsToInventory(yggdrasil);
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilNature, 0);

        whenTowerIsReplaced(tower, TowerType.Hitman);

        Hitman hitman = unitGateway.findUnit(Hitman.class, wizard.playerId);
        assertThat(hitman.getItem(0)).isNull();
        assertThat(wizard.itemStash.get(ItemType.BranchOfYggdrasilNature).amount).isEqualTo(1);
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

    private void thenInventoryIsFilledWithBranches() {
        assertThat(yggdrasil.getItem(0)).isInstanceOf(BranchOfYggdrasilNature.class);
        assertThat(yggdrasil.getItem(1)).isInstanceOf(BranchOfYggdrasilMetropolis.class);
        assertThat(yggdrasil.getItem(2)).isInstanceOf(BranchOfYggdrasilDarkness.class);
        assertThat(yggdrasil.getItem(3)).isInstanceOf(BranchOfYggdrasilLight.class);
    }
}