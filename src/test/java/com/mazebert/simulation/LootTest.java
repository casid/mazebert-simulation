package com.mazebert.simulation;

import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LootTest extends SimTest {

    private static final float BABY_SWORD_ROLL = 0.42f;
    private static final float BABY_SWORD_ROLL_ILVL_1 = 0.58f;
    private static final float WOODEN_STAFF_ROLL = 0.0f;

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();
    DamageSystemTrainer damageSystemTrainer;

    Wizard wizard;
    Tower tower;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        damageSystem = damageSystemTrainer = new DamageSystemTrainer(simulationListeners);
        lootSystem = new LootSystem(randomPlugin, simulationListeners);

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        tower.setWizard(wizard);
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(1.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());

        damageSystemTrainer.givenConstantDamage(1000); // one shot!

        creep = new Creep();
        Wave wave = new Wave();
        wave.round = 1;
        creep.setWave(wave);
        creep.setMaxItemLevel(100);
        unitGateway.addUnit(creep);
    }

    @Test
    void loot_babySword() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.99f, // The rarity of this drop is common
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
                0.99f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL, // It's a baby sword!
                0.0f, // This is a drop
                0.99f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL // It's a baby sword!
        );
        creep.setMaxDrops(2);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(2);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BabySword);
    }

    @Test
    void loot_woodenStaff() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.99f, // The rarity of this drop is common
                0.0f, // This is an item drop
                WOODEN_STAFF_ROLL // It's a wooden staff!
        );
        creep.setMaxDrops(1);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.WoodenStaff);
    }

    @Test
    void loot_oneBabySwordOnSecondTry() {
        randomPluginTrainer.givenFloatAbs(
                0.9f, // No drop
                0.0f, // This is a drop
                0.99f, // The rarity of this drop is common
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
                0.99f, // The rarity of this drop is common
                0.0f, // This is an item drop
                BABY_SWORD_ROLL, // It's a baby sword!
                // Guaranteed drop, no need to roll
                0.99f, // The rarity of this drop is common
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
    void loot_legendary_noneAvailable() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.001f, // The rarity of this drop is legendary
                0.0f, // This is an item drop
                BABY_SWORD_ROLL_ILVL_1 // It's a baby sword!
        );
        creep.setMaxDrops(1);
        creep.setMaxItemLevel(1);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BabySword);
    }

    @Test
    void loot_itemLevel_tooLow() {
        creep.setMaxItemLevel(0);
        creep.setMinDrops(1);
        creep.setMaxDrops(1);

        whenTowerAttacks();

        assertThat(wizard.itemStash.size()).isEqualTo(0);
    }

    @Test
    void loot_itemLevel_onlyOneFit() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.01f, // The rarity of this drop is uncommon
                0.0f, // This is an item drop
                0.9f // It's a meat mallet!
        );
        creep.setMaxDrops(1);

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.MeatMallet);
    }

    @Test
    void loot_itemLevel_onlyOneFit2() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.01f, // The rarity of this drop is uncommon
                0.0f, // This is an item drop
                0.9f // It's a meat mallet!
        );
        creep.setMaxDrops(1);
        creep.setMaxItemLevel(ItemType.MonsterTeeth.instance.getItemLevel());

        whenTowerAttacks();

        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.MonsterTeeth);
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

    @Test
    void loot_potion() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // This is a drop
                0.99f, // The rarity of this drop is common
                0.9f, // This is a potion drop
                0.0f // It's a common damage potion!
        );
        creep.setMaxDrops(1);

        whenTowerAttacks();

        assertThat(wizard.potionStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.CommonDamage);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }
}