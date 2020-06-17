package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class FrozenTest extends ItemTest {

    @BeforeEach
    void setUp() {
        projectileGateway = new ProjectileGateway();

        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
    }

    @Override
    protected Tower createTower() {
        version = Sim.v22; // Can be removed after we past that version
        return super.createTower();
    }

    @Test
    void twoItems() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);

        whenTowerAttacks();

        assertThat(creep.getSpeedModifier()).isEqualTo(0.9f);
    }

    @Test
    void twoItems_oneDropped() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);
        whenItemIsEquipped(null, 0);

        whenTowerAttacks();

        assertThat(creep.getSpeedModifier()).isEqualTo(1.0f);
    }

    @Test
    void twoItems_oneReplaced() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);

        whenTowerAttacks();

        assertThat(creep.getSpeedModifier()).isEqualTo(0.9f);
    }

    @Test
    void fourItems() {
        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);
        whenItemIsEquipped(ItemType.FrozenBook, 2);
        whenItemIsEquipped(ItemType.FrozenCandle, 3);

        whenTowerAttacksAfter60Seconds();

        assertThat(wizard.towerStash.get(TowerType.Gib).getAmount()).isEqualTo(1);
        assertThat(tower.getItem(0)).isNull();
        assertThat(tower.getItem(1)).isNull();
        assertThat(tower.getItem(2)).isNull();
        assertThat(tower.getItem(3)).isNull();
        assertThat(wizard.itemStash.size()).isEqualTo(0);
        assertThat(tower.getAbility(FrozenSetSummonAbility.class)).isNull();
    }

    @Test
    void fiveItems() {
        tower.addInventorySize(1);
        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);
        whenItemIsEquipped(ItemType.FrozenBook, 2);
        whenItemIsEquipped(ItemType.FrozenCandle, 3);
        whenItemIsEquipped(ItemType.FrozenBook, 4); // Another frozen item equipped

        whenTowerAttacksAfter60Seconds();

        assertThat(wizard.towerStash.get(TowerType.Gib).getAmount()).isEqualTo(1);
        assertThat(tower.getItem(0)).isNull();
        assertThat(tower.getItem(1)).isNull();
        assertThat(tower.getItem(2)).isNotNull().isInstanceOf(FrozenBook.class); // Is not removed.
        assertThat(tower.getItem(3)).isNull();
        assertThat(tower.getItem(4)).isNull();
        assertThat(wizard.itemStash.size()).isEqualTo(0);
        assertThat(tower.getAbility(FrozenSetSummonAbility.class)).isNull();
    }

    @Test
    void knusper_eat() {
        wizard.gold = 1000000;
        tower = whenTowerIsReplaced(this.tower, TowerType.KnusperHexe);
        whenItemIsEquipped(ItemType.FrozenWater, 0);
        whenItemIsEquipped(ItemType.FrozenHeart, 1);
        whenItemIsEquipped(ItemType.FrozenBook, 2);
        whenItemIsEquipped(ItemType.FrozenCandle, 3);

        tower.simulate(60.0f);

        Creep creep = a(creep().mass());
        unitGateway.addUnit(creep);
        damageSystemTrainer.givenConstantDamage(0);

        whenTowerAttacks();

        assertThat(wizard.towerStash.get(TowerType.Gib).getAmount()).isEqualTo(1);
    }

    private void whenTowerAttacksAfter60Seconds() {
        tower.simulate(60.0f);

        Creep creep = a(creep());
        unitGateway.addUnit(creep);
        damageSystemTrainer.givenConstantDamage(1000);

        whenTowerAttacks();
    }
}