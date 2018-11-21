package com.mazebert.simulation;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.DamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LootTest extends SimTest {

    private static final float BABY_SWORD_ROLL = 0.6f;

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;
    Tower tower;
    Creep creep;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;

        // Setup of tower is so that no random numbers are required for damage calculation
        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(1.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new DamageAbility());
        tower.setCritChance(0.0f);
        tower.setDamageSpread(0);
        tower.setBaseDamage(1000);
        tower.setMulticrit(0);

        creep = new Creep();
        unitGateway.addUnit(creep);

        wizard = new Wizard();
        unitGateway.addUnit(wizard);
    }

    @Test
    void loot_babySword() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.0f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL // It's a baby sword!
        );
        creep.setMaxDrops(1);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BabySword);
    }

    @Test
    void loot_twoBabySwords() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.0f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL, // It's a baby sword!
                0.0f, // This is a drop
                0.0f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL // It's a baby sword!
        );
        creep.setMaxDrops(2);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(2);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BabySword);
    }

    @Test
    void loot_oneBabySwordOnSecondTry() {
        randomPluginTrainer.givenFloatAbs(
                0.9f, // No drop
                0.0f, // This is a drop
                0.0f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL // It's a baby sword!
        );
        creep.setMaxDrops(2);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BabySword);
    }

    @Test
    void loot_twoGuaranteedBabySwords() {
        randomPluginTrainer.givenFloatAbs(
                // Guaranteed drop, no need to roll
                0.0f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL, // It's a baby sword!
                // Guaranteed drop, no need to roll
                0.0f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL // It's a baby sword!
        );
        creep.setMinDrops(2);
        creep.setMaxDrops(2);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(2);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BabySword);
    }

    @Test
    void loot_nothing() {
        randomPluginTrainer.givenFloatAbs(
                0.9f // No drop
        );
        creep.setMaxDrops(1);

        whenTowerAttacks();

        assertThat(wizard.itemStash.size()).isEqualTo(0);
    }
    
    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }
}