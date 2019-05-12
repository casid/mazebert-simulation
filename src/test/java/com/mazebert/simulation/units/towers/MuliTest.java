package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class MuliTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();
    DamageSystemTrainer damageSystemTrainer = new DamageSystemTrainer();

    Wizard wizard;
    Muli muli;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();

        randomPlugin = randomPluginTrainer;
        damageSystem = damageSystemTrainer;
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new BloodMoor();
        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard = new Wizard();
        wizard.gold = 100;
        unitGateway.addUnit(wizard);

        muli = new Muli();
        muli.setWizard(wizard);
        unitGateway.addUnit(muli);
    }

    @Test
    void muliCannotAttackWithoutBananas() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenMuliAttacks();

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void muliGainsBananasFromHuli() {
        givenHuliProvidesBanana();
        thenBanansAre(1);
    }

    @Test
    void huliCannotAttackCreepsWhenMuliIsAround() {
        Huli huli = new Huli();
        unitGateway.addUnit(huli);

        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenHuliAttacks(huli);

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void huliCannotAttackCreepsWhenMuliIsAround_huliBuiltBeforeMuli() {
        unitGateway.removeUnit(muli);
        Huli huli = new Huli();
        unitGateway.addUnit(huli);
        muli = new Muli();
        unitGateway.addUnit(muli);

        whenHuliAttacks(huli);

        thenBanansAre(1);
    }

    @Test
    void muliUsesBananasToAttack() {
        givenHuliProvidesBanana();

        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenMuliAttacks();

        assertThat(creep.getHealth()).isEqualTo(90);
        thenBanansAre(0);
    }

    @Test
    void muliDrinksLiquorWhenNothingToAttack() {
        whenMuliAttacks();

        assertThat(muli.getCritChance()).isEqualTo(Balancing.STARTING_CRIT_CHANCE + MuliBooze.CRIT_CHANCE_ADD);
        assertThat(muli.getCritDamage()).isEqualTo(Balancing.STARTING_CRIT_DAMAGE + MuliBooze.CRIT_DAMAGE_ADD);
        assertThat(wizard.gold).isEqualTo(100 - MuliBooze.GOLD);
    }

    @Test
    void muliDrinksLiquorWhenTargetLeavesRange() {
        givenHuliProvidesBanana();
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenMuliAttacks();
        creep.setX(100);
        whenMuliAttacks();

        assertThat(muli.getState()).isEqualTo(MuliState.Drunk);
    }

    @Test
    void notEnoughGoldForLiquor() {
        wizard.gold = MuliBooze.GOLD - 1;

        whenMuliAttacks();

        assertThat(muli.getCritChance()).isEqualTo(Balancing.STARTING_CRIT_CHANCE);
        assertThat(muli.getCritDamage()).isEqualTo(Balancing.STARTING_CRIT_DAMAGE);
        assertThat(wizard.gold).isEqualTo(MuliBooze.GOLD - 1);
    }

    @Test
    void exactlyEnoughGoldForLiquor() {
        wizard.gold = MuliBooze.GOLD;

        whenMuliAttacks();

        assertThat(wizard.gold).isEqualTo(0);
    }

    @Test
    void muliHasHangoverAfterLiquor() {
        givenHuliProvidesBanana();
        givenHuliProvidesBanana();
        givenHuliProvidesBanana();

        // Getting drunk
        whenMuliAttacks();
        assertThat(muli.getState()).isEqualTo(MuliState.Drunk);

        // Creep arrives
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        // Cannot attack, hangover is triggered instead
        whenMuliAttacks();
        assertThat(muli.getState()).isEqualTo(MuliState.Hangover);
        assertThat(creep.getHealth()).isEqualTo(100);

        // Recovers after hangover duration
        muli.simulate(MuliBooze.HANGOVER_DURATION);
        assertThat(muli.getState()).isEqualTo(MuliState.Normal);

        // Now its possible to attack again
        whenMuliAttacks();
        assertThat(creep.getHealth()).isEqualTo(90);
    }

    @Test
    void chanceToBuyLiquor_notRolled() {
        randomPluginTrainer.givenFloatAbs(MuliBooze.CHANCE + 0.001f);
        whenMuliAttacks();
        assertThat(muli.getState()).isEqualTo(MuliState.Normal);
    }

    @Test
    void chanceToBuyLiquor_rolled() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        whenMuliAttacks();
        assertThat(muli.getState()).isEqualTo(MuliState.Drunk);
    }

    @Test
    void muliAndTrident() {
        givenItemIsEquipped(ItemType.Trident);
        givenHuliProvidesBananas(10);

        // Creep arrives
        Creep creep1 = new Creep();
        creep1.setHealth(11);
        unitGateway.addUnit(creep1);

        whenMuliAttacks();

        // Another creep arrives
        Creep creep2 = new Creep();
        unitGateway.addUnit(creep2);

        whenMuliAttacks();

        assertThat(creep1.getHealth()).isEqualTo(0.0f);
        assertThat(creep2.getHealth()).isEqualTo(90.0f);

        whenMuliAttacks();

        assertThat(creep2.getHealth()).isEqualTo(80.0f);
    }

    private void givenHuliProvidesBanana() {
        givenHuliProvidesBananas(1);
    }

    private void givenHuliProvidesBananas(int bananas) {
        Huli huli = new Huli();
        unitGateway.addUnit(huli);
        for (int i = 0; i < bananas; ++i) {
            whenHuliAttacks(huli);
        }
    }

    private void whenHuliAttacks(Huli huli) {
        huli.simulate(huli.getBaseCooldown());
        projectileGateway.simulate(0.1f);
        projectileGateway.simulate(0.1f);
    }

    private void whenMuliAttacks() {
        muli.simulate(muli.getCooldown());
        projectileGateway.simulate(0.1f);
        projectileGateway.simulate(0.1f);
    }

    @SuppressWarnings("SameParameterValue")
    private void givenItemIsEquipped(ItemType itemType) {
        wizard.itemStash.add(itemType);
        EquipItemCommand command = new EquipItemCommand();
        command.playerId = wizard.getPlayerId();
        command.inventoryIndex = 0;
        command.itemType = itemType;
        command.towerX = 0;
        command.towerY = 0;
        commandExecutor.executeVoid(command);
    }

    private void thenBanansAre(int bananas) {
        CustomTowerBonus bonus = new CustomTowerBonus();
        muli.populateCustomTowerBonus(bonus);
        assertThat(bonus.value).isEqualTo("" + bananas);
    }
}