package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.Simulation;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.ActivateAbilityCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.FrozenBook;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.ScepterOfTime;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MrIronTest extends SimTest {
    Wizard wizard;
    MrIron mrIron;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        damageSystem = new DamageSystemTrainer();
        playerGateway = new PlayerGatewayTrainer();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        mrIron = new MrIron();
        mrIron.setWizard(wizard);
        unitGateway.addUnit(mrIron);

        commandExecutor = new CommandExecutor();

        simulation = new Simulation();
    }

    @Test
    void construct() {
        givenItemIsEquipped(ItemType.BabySword, 0);

        whenAbilityIsActivated();

        assertThat(mrIron.getItem(0)).isNull();
        assertThat(mrIron.getAddedRelativeBaseDamage()).isEqualTo(0.1f);
    }

    @Test
    void construct_fullInventory() {
        givenItemIsEquipped(ItemType.BabySword, 0);
        givenItemIsEquipped(ItemType.BabySword, 1);
        givenItemIsEquipped(ItemType.BabySword, 2);
        givenItemIsEquipped(ItemType.BabySword, 3);

        whenAbilityIsActivated();

        assertThat(mrIron.getItem(0)).isNull();
        assertThat(mrIron.getItem(1)).isNull();
        assertThat(mrIron.getItem(2)).isNull();
        assertThat(mrIron.getItem(3)).isNull();
        assertThat(mrIron.getAddedRelativeBaseDamage()).isEqualTo(0.4f);
    }

    @Test
    void construct_noUniques() {
        givenItemIsEquipped(ItemType.ScepterOfTime, 0);

        whenAbilityIsActivated();

        assertThat(mrIron.getItem(0)).isInstanceOf(ScepterOfTime.class);
    }

    @Test
    void construct_noSets() {
        givenItemIsEquipped(ItemType.FrozenBook, 0);

        whenAbilityIsActivated();

        assertThat(mrIron.getItem(0)).isInstanceOf(FrozenBook.class);
    }

    @Test
    void construct_cannotAttack() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenAbilityIsActivated();

        mrIron.simulate(mrIron.getBaseCooldown());
        assertThat(creep.getHealth()).isEqualTo(100);

        mrIron.simulate(MrIronConstruct.COOLDOWN);
        assertThat(creep.getHealth()).isEqualTo(100);

        mrIron.simulate(mrIron.getBaseCooldown());
        assertThat(creep.getHealth()).isEqualTo(80);
    }

    @Test
    void canAttackRegulary() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        mrIron.simulate(mrIron.getBaseCooldown());

        assertThat(creep.getHealth()).isEqualTo(80);
    }

    private void givenItemIsEquipped(ItemType itemType, int inventoryIndex) {
        wizard.itemStash.add(itemType);
        EquipItemCommand command = new EquipItemCommand();
        command.itemType = itemType;
        command.inventoryIndex = inventoryIndex;
        command.playerId = wizard.getPlayerId();
        commandExecutor.executeVoid(command);
    }

    private void whenAbilityIsActivated() {
        ActivateAbilityCommand command = new ActivateAbilityCommand();
        command.abilityType = ActiveAbilityType.MrIronConstruct;
        commandExecutor.executeVoid(command);
    }
}