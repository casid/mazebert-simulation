package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.towers.Hitman;
import com.mazebert.simulation.units.towers.Mummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class CreepExperienceTest extends SimTest {
    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        projectileGateway = new ProjectileGateway();
        unitGateway = new UnitGateway();
        randomPlugin = new RandomPluginTrainer();
        damageSystem = new DamageSystemTrainer();
        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();
    }

    @Test
    void name() {
        Hitman hitman = new Hitman();
        unitGateway.addUnit(hitman);

        Mummy mummy = new Mummy();
        unitGateway.addUnit(mummy);

        Creep creep = a(creep().boss());
        unitGateway.addUnit(creep);

        whenTowerAttacks(hitman);
        whenTowerAttacks(hitman);
        mummy.kill(creep);

        assertThat(creep.getHealth()).isEqualTo(0);
        assertThat(hitman.getExperience()).isEqualTo(0.4f);
        assertThat(mummy.getExperience()).isEqualTo(1.6f);
    }
}