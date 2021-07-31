package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.Simulation;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.TestMap;
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
        gameGateway = new GameGateway();
        damageSystem = new DamageSystemTrainer();
        playerGateway = new PlayerGatewayTrainer();
        waveGateway = new WaveGateway();

        gameGateway.getGame().map = new TestMap(1);

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
    void construct_mushroom() {
        givenItemIsEquipped(ItemType.MagicMushroom, 0);
        givenItemIsEquipped(ItemType.MagicMushroom, 1);
        givenItemIsEquipped(ItemType.MagicMushroom, 2);

        whenAbilityIsActivated();

        assertThat(mrIron.getAddedRelativeBaseDamage()).isEqualTo(-0.6f);
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

        simulationListeners.onUpdate.dispatch(MrIronConstruct.COOLDOWN);
        assertThat(creep.getHealth()).isEqualTo(100);

        mrIron.simulate(mrIron.getBaseCooldown());
        assertThat(creep.getHealth()).isEqualTo(80);
    }

    @Test
    void construct_cooldown_faster() {
        whenItemIsEquipped(mrIron, ItemType.SevenLeaguesBoots, 0);
        whenItemIsEquipped(mrIron, ItemType.SevenLeaguesBoots, 1);

        assertThat(mrIron.getAbility(MrIronConstruct.class).getCooldown()).isEqualTo(45.0f);
    }

    @Test
    void construct_cooldown_slower() {
        whenItemIsEquipped(mrIron, ItemType.EldritchClaw, 0);
        whenItemIsEquipped(mrIron, ItemType.EldritchClaw, 1);

        assertThat(mrIron.getAbility(MrIronConstruct.class).getCooldown()).isEqualTo(264.7059f);
    }

    @Test
    void construct_cooldown_slowest() {
        whenItemIsEquipped(mrIron, ItemType.EldritchClaw, 0);
        whenItemIsEquipped(mrIron, ItemType.EldritchClaw, 1);
        whenItemIsEquipped(mrIron, ItemType.EldritchClaw, 2);
        whenItemIsEquipped(mrIron, ItemType.EldritchClaw, 3);

        assertThat(mrIron.getAbility(MrIronConstruct.class).getCooldown()).isEqualTo(MrIronConstruct.MAX_COOLDOWN);
    }

    @Test
    void canAttackRegulary() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        mrIron.simulate(mrIron.getBaseCooldown());

        assertThat(creep.getHealth()).isEqualTo(80);
    }

    private void givenItemIsEquipped(ItemType itemType, int inventoryIndex) {
        whenItemIsEquipped(mrIron, itemType, inventoryIndex);
    }

    private void whenAbilityIsActivated() {
        whenAbilityIsActivated(mrIron, ActiveAbilityType.MrIronConstruct);
    }
}