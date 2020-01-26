package com.mazebert.simulation.util;

import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DamageMapTest {

    @Test
    void one() {
        DamageMap damageMap = new DamageMap();
        TestTower tower = new TestTower();

        damageMap.add(tower, 10);
        damageMap.add(tower, 20);
        damageMap.add(tower, 5);
        damageMap.add(tower, 5);

        assertThat(damageMap.get(tower)).isEqualTo(40.0);
    }

    @Test
    void three_smallCapacity() {
        DamageMap damageMap = new DamageMap(2);
        TestTower tower1 = new TestTower();
        TestTower tower2 = new TestTower();
        TestTower tower3 = new TestTower();

        damageMap.add(tower1, 10);
        damageMap.add(tower2, 20);
        damageMap.add(tower3, 5);
        damageMap.add(tower1, 5);

        assertThat(damageMap.get(tower1)).isEqualTo(15.0);
        assertThat(damageMap.get(tower2)).isEqualTo(20.0);
        assertThat(damageMap.get(tower3)).isEqualTo(5.0);
    }

    @Test
    void notFound() {
        DamageMap damageMap = new DamageMap();
        assertThat(damageMap.get(new TestTower())).isEqualTo(0.0);
    }

    @Test
    void null_makesNoSenseButWorks() {
        DamageMap damageMap = new DamageMap();

        damageMap.add(null, 1);
        damageMap.add(null, 2);

        assertThat(damageMap.get(null)).isEqualTo(3.0);
    }

    @Test
    void normalize_empty() {
        DamageMap damageMap = new DamageMap();
        damageMap.forEachNormalized((t, d) -> assertThat(d).isEqualTo(0.0));
    }

    @Test
    void normalize_oneWithZeroDamage() {
        DamageMap damageMap = new DamageMap();
        TestTower tower = new TestTower();

        damageMap.add(tower, 0);
        damageMap.forEachNormalized((t, d) -> assertThat(d).isEqualTo(0.0));
    }

    @Test
    void normalize_oneWithDamage() {
        DamageMap damageMap = new DamageMap();
        TestTower tower = new TestTower();

        damageMap.add(tower, 250);

        damageMap.forEachNormalized((t, d) -> assertThat(d).isEqualTo(1.0));
    }

    @Test
    void normalize_twoWithDamage() {
        DamageMap damageMap = new DamageMap();
        TestTower tower1 = new TestTower();
        TestTower tower2 = new TestTower();

        damageMap.add(tower1, 100);
        damageMap.add(tower2, 100);
        damageMap.forEachNormalized((t, d) -> assertThat(d).isEqualTo(0.5));
    }
}