package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class SpiderTest extends ItemTest {

    Creep creep;

    @BeforeEach
    void setUp() {
        creep = a(creep());
        unitGateway.addUnit(creep);
    }

    @Override
    protected Tower createTower() {
        projectileGateway = new ProjectileGateway();
        return new Spider();
    }

    @Test
    void web() {
        whenTowerAttacks();
        assertThat(creep.getAbility(SpiderWebEffect.class).getStackCount()).isEqualTo(1);
        assertThat(creep.getImmobilizeResistance()).isEqualTo(0.05f);
    }

    @Test
    void web_steady() {
        creep.setSteady(true);
        whenTowerAttacks();
        assertThat(creep.getAbility(SpiderWebEffect.class)).isNull();
    }

    @Test
    void web_immobilizeResistance() {
        creep.addImmobilizeResistance(1.0f);
        whenTowerAttacks();
        assertThat(creep.getAbility(SpiderWebEffect.class)).isNull();
    }

    @Override
    protected void whenTowerAttacks() {
        super.whenTowerAttacks();
        projectileGateway.simulate(1.0f);
        projectileGateway.simulate(1.0f);
    }
}