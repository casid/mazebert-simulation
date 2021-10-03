package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class IdunTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new Idun();
    }

    @Test
    void reviveEffect() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        assertThat(creep.hasAbility(IdunReviveAuraEffect.class)).isTrue();
    }

    @Test
    void maxLevel_wizardHealthChanged() {
        Tower rabbit = whenTowerNeighbourIsBuilt(this.tower, TowerType.Rabbit, 1, 0);
        assertThat(rabbit.getMaxLevel()).isEqualTo(50);

        wizard.addHealth(1.0f);
        assertThat(rabbit.getMaxLevel()).isEqualTo(100);

        wizard.addHealth(1.0f);
        assertThat(rabbit.getMaxLevel()).isEqualTo(150);

        wizard.addHealth(-1.0f);
        assertThat(rabbit.getMaxLevel()).isEqualTo(100);
    }

    @Test
    void maxLevel_idunSold() {
        Tower rabbit = whenTowerNeighbourIsBuilt(this.tower, TowerType.Rabbit, 1, 0);
        wizard.addHealth(1.0f);
        assertThat(rabbit.getMaxLevel()).isEqualTo(100);

        whenTowerIsSold();

        assertThat(rabbit.getMaxLevel()).isEqualTo(99);
    }
}