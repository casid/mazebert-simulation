package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VikingTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();
    DamageSystemTrainer damageSystemTrainer;

    Wizard wizard;
    Viking viking;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        randomPlugin = randomPluginTrainer;
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();
        damageSystem = damageSystemTrainer = new DamageSystemTrainer();
        lootSystem = new LootSystemTrainer();
        experienceSystem = new ExperienceSystem();

        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        viking = new Viking();
        viking.setWizard(wizard);
        unitGateway.addUnit(viking);

        creep = new Creep();
        unitGateway.addUnit(creep);
    }

    @Test
    void attack_canHitSameCreepWithTwoAxesAtOnce() {
        whenVikingAttacks();

        assertThat(creep.getHealth()).isEqualTo(80);
    }

    @Test
    void attack_canHitTwoCreeps() {
        Creep anotherCreep = new Creep();
        unitGateway.addUnit(anotherCreep);

        whenVikingAttacks();

        assertThat(creep.getHealth()).isEqualTo(90);
        assertThat(anotherCreep.getHealth()).isEqualTo(90);
    }

    @Test
    void kill_canDropMead_drop() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        damageSystemTrainer.givenConstantDamage(1000);

        whenVikingAttacks();

        assertThat(wizard.potionStash.get(PotionType.Mead).getAmount()).isEqualTo(1);
    }

    @Test
    void kill_canDropMead_noDrop() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        damageSystemTrainer.givenConstantDamage(1000);

        whenVikingAttacks();

        assertThat(wizard.potionStash.get(PotionType.Mead)).isNull();
    }

    @Test
    void kill_canDropMead_badDropChanceOfCreep_noDrop() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        damageSystemTrainer.givenConstantDamage(1000);
        creep.setDropChance(0);

        whenVikingAttacks();

        assertThat(wizard.potionStash.get(PotionType.Mead)).isNull();
    }

    @Test
    void drinksMead() {
        givenMeadPotionIsDrank();
        givenMeadPotionIsDrank();
        givenMeadPotionIsDrank();

        CustomTowerBonus bonus = new CustomTowerBonus();
        viking.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Mead:");
        assertThat(bonus.value).isEqualTo("3");
    }

    @Test
    void drinksNoMead() {
        CustomTowerBonus bonus = new CustomTowerBonus();
        viking.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Mead:");
        assertThat(bonus.value).isEqualTo("0");
    }

    @Test
    void drinksMead_hasEffects() {
        givenMeadPotionIsDrank();
        assertThat(viking.getChanceToMiss()).isEqualTo(0.01f);
        givenMeadPotionIsDrank();
        assertThat(viking.getChanceToMiss()).isEqualTo(0.02f);
    }

    private void givenMeadPotionIsDrank() {
        wizard.potionStash.add(PotionType.Mead);
        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = PotionType.Mead;
        command.playerId = wizard.getPlayerId();
        commandExecutor.executeVoid(command);
    }

    private void whenVikingAttacks() {
        viking.simulate(3.0f); // attack
        projectileGateway.simulate(1.0f); // projectile spawning
        projectileGateway.simulate(1.0f); // projectile hitting
    }
}