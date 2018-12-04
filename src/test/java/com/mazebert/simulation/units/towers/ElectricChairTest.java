package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElectricChairTest extends SimTest {
    ElectricChair electricChair;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        damageSystem = new DamageSystemTrainer();

        electricChair = new ElectricChair();
        unitGateway.addUnit(electricChair);
    }

    @Test
    void attack_one() {
        Creep creep1 = new Creep();
        unitGateway.addUnit(creep1);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
    }

    @Test
    void attack_two() {
        Creep creep1 = new Creep();
        unitGateway.addUnit(creep1);
        Creep creep2 = new Creep();
        creep2.setX(100);
        creep2.setY(100);
        unitGateway.addUnit(creep2);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
    }

    @Test
    void attack_three() {
        Creep creep1 = new Creep();
        unitGateway.addUnit(creep1);
        Creep creep2 = new Creep();
        unitGateway.addUnit(creep2);
        Creep creep3 = new Creep();
        unitGateway.addUnit(creep3);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
        assertThat(creep3.getHealth()).isEqualTo(100);
    }

    @Test
    void attack_three_oneDead() {
        Creep creep1 = new Creep();
        unitGateway.addUnit(creep1);
        Creep creep2 = new Creep();
        creep2.setHealth(0);
        unitGateway.addUnit(creep2);
        Creep creep3 = new Creep();
        unitGateway.addUnit(creep3);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(0);
        assertThat(creep3.getHealth()).isEqualTo(90);
    }

    @Test
    void attack_three_afterLevelUp() {
        Creep creep1 = new Creep();
        unitGateway.addUnit(creep1);
        Creep creep2 = new Creep();
        unitGateway.addUnit(creep2);
        Creep creep3 = new Creep();
        unitGateway.addUnit(creep3);

        electricChair.setLevel(14);
        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
        assertThat(creep3.getHealth()).isEqualTo(90);
    }

    @Test
    void customBonus() {
        CustomTowerBonus bonus = new CustomTowerBonus();
        electricChair.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Chains:");
        assertThat(bonus.value).isEqualTo("+1");
    }

    @Test
    void customBonus_level() {
        electricChair.setLevel(14 * 2);

        CustomTowerBonus bonus = new CustomTowerBonus();
        electricChair.populateCustomTowerBonus(bonus);

        assertThat(bonus.title).isEqualTo("Chains:");
        assertThat(bonus.value).isEqualTo("+3");
    }

    private void whenTowerAttacks() {
        electricChair.simulate(electricChair.getBaseCooldown());
    }
}