package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnChainListener;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ChainViewType;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class ElectricChairTest extends SimTest implements OnChainListener {
    DamageSystemTrainer damageSystemTrainer = new DamageSystemTrainer();

    ElectricChair electricChair;
    Creep target;
    Creep[] chained;

    @BeforeEach
    void setUp() {
        version = Sim.vDoLEnd;

        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = new RandomPluginTrainer();

        damageSystem = damageSystemTrainer;
        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystem();
        gameSystem = new GameSystem();

        electricChair = new ElectricChair();
        electricChair.onChain.add(this);
        unitGateway.addUnit(electricChair);
    }

    @Test
    void attack_one() {
        Creep creep1 = a(creep());
        unitGateway.addUnit(creep1);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
    }

    @Test
    void attack_two() {
        Creep creep1 = a(creep());
        unitGateway.addUnit(creep1);
        Creep creep2 = a(creep());
        creep2.setX(100);
        creep2.setY(100);
        unitGateway.addUnit(creep2);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
    }

    @Test
    void attack_two_firstKilled() {
        Creep creep1 = a(creep());
        creep1.setHealth(1);
        unitGateway.addUnit(creep1);
        Creep creep2 = a(creep());
        unitGateway.addUnit(creep2);

        whenTowerAttacks();

        assertThat(target).isSameAs(creep1);
        assertThat(chained[0]).isSameAs(creep2);
    }

    @Test
    void attack_two_lastKilled() {
        Creep creep1 = a(creep());
        unitGateway.addUnit(creep1);
        Creep creep2 = a(creep());
        creep2.setHealth(1);
        unitGateway.addUnit(creep2);

        whenTowerAttacks();

        assertThat(target).isSameAs(creep1);
        assertThat(chained[0]).isSameAs(creep2);
    }

    @Test
    void attack_four() {
        Creep creep1 = a(creep());
        unitGateway.addUnit(creep1);
        Creep creep2 = a(creep());
        unitGateway.addUnit(creep2);
        Creep creep3 = a(creep());
        unitGateway.addUnit(creep3);
        Creep creep4 = a(creep());
        unitGateway.addUnit(creep4);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
        assertThat(creep3.getHealth()).isEqualTo(90);
        assertThat(creep4.getHealth()).isEqualTo(100);
    }

    @Test
    void attack_four_oneDead() {
        Creep creep1 = a(creep());
        unitGateway.addUnit(creep1);
        Creep creep2 = a(creep());
        creep2.setHealth(0);
        unitGateway.addUnit(creep2);
        Creep creep3 = a(creep());
        unitGateway.addUnit(creep3);
        Creep creep4 = a(creep());
        unitGateway.addUnit(creep4);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(0);
        assertThat(creep3.getHealth()).isEqualTo(90);
        assertThat(creep4.getHealth()).isEqualTo(90);
    }

    @Test
    void attack_four_afterLevelUp() {
        Creep creep1 = a(creep());
        unitGateway.addUnit(creep1);
        Creep creep2 = a(creep());
        unitGateway.addUnit(creep2);
        Creep creep3 = a(creep());
        unitGateway.addUnit(creep3);
        Creep creep4 = a(creep());
        unitGateway.addUnit(creep4);

        electricChair.setLevel(14);
        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
        assertThat(creep3.getHealth()).isEqualTo(90);
        assertThat(creep4.getHealth()).isEqualTo(90);
    }

    @Test
    void levelsUpDuringChain() {
        damageSystemTrainer.givenConstantDamage(1000);
        Creep creep1 = a(creep());
        unitGateway.addUnit(creep1);
        Creep creep2 = a(creep());
        unitGateway.addUnit(creep2);
        Creep creep3 = a(creep());
        unitGateway.addUnit(creep3);

        // When the chair kills creep 1, it will level up and gain +1 chain
        electricChair.setExperience(Balancing.getTowerExperienceForLevel(28) - 1);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(0);
        assertThat(creep2.getHealth()).isEqualTo(0);
        assertThat(creep3.getHealth()).isEqualTo(0);
    }

    @Test
    void customBonus() {
        CustomTowerBonus bonus = new CustomTowerBonus();
        electricChair.populateCustomTowerBonus(bonus);
        assertThat(bonus.title).isEqualTo("Chains:");
        assertThat(bonus.value).isEqualTo("2");
    }

    @Test
    void customBonus_level() {
        electricChair.setLevel(7 * 2);

        CustomTowerBonus bonus = new CustomTowerBonus();
        electricChair.populateCustomTowerBonus(bonus);

        assertThat(bonus.title).isEqualTo("Chains:");
        assertThat(bonus.value).isEqualTo("3");
    }

    private void whenTowerAttacks() {
        electricChair.simulate(electricChair.getBaseCooldown());
    }

    @Override
    public void onChain(ChainViewType viewType, Creep target, Creep[] chained, int chainedLength) {
        this.target = target;
        this.chained = chained.clone();
    }
}