package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MuliTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Muli muli;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();

        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystemTrainer();

        muli = new Muli();
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
    void muliUsesBananasToAttack() {
        givenHuliProvidesBanana();

        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        whenMuliAttacks();

        assertThat(creep.getHealth()).isEqualTo(90);
        thenBanansAre(0);
    }

    private void givenHuliProvidesBanana() {
        Huli huli = new Huli();
        unitGateway.addUnit(huli);
        whenHuliAttacks(huli);
    }

    private void whenHuliAttacks(Huli huli) {
        huli.simulate(huli.getBaseCooldown());
        projectileGateway.simulate(0.1f);
        projectileGateway.simulate(0.1f);
    }

    private void whenMuliAttacks() {
        muli.simulate(muli.getBaseCooldown());
        projectileGateway.simulate(0.1f);
        projectileGateway.simulate(0.1f);
    }

    private void thenBanansAre(int bananas) {
        CustomTowerBonus bonus = new CustomTowerBonus();
        muli.populateCustomTowerBonus(bonus);
        assertThat(bonus.value).isEqualTo("" + bananas);
    }
}