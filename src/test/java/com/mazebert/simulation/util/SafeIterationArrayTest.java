package com.mazebert.simulation.util;

import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.Unit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SafeIterationArrayTest {
    SafeIterationArray<Unit> units = new SafeIterationArray<>();

    @Test
    void removeAllWhileIterating() {
        units.add(new TestTower());
        units.add(new TestTower());
        units.add(new TestTower());

        units.forEach(units::remove);

        assertThat(units.size()).isEqualTo(0);
    }

    @Test
    void nestedIteration_removes() {
        TestTower e1 = new TestTower();
        units.add(e1);
        TestTower e2 = new TestTower();
        units.add(e2);
        TestTower e3 = new TestTower();
        units.add(e3);

        units.forEach(u -> {
            units.forEach(u2 -> {
                if (u == e2) {
                    units.remove(u);
                }
            });
            if (u == e3) {
                units.remove(u);
            }
        });

        assertThat(units.size()).isEqualTo(1);
        assertThat(units.get(0)).isSameAs(e1);
    }

    @Test
    void addOneWhileIterating() {
        TestTower e1 = new TestTower();
        units.add(e1);
        TestTower e2 = new TestTower();
        units.add(e2);
        TestTower e3 = new TestTower();
        units.add(e3);
        TestTower e4 = new TestTower();

        units.forEach(u -> {
            if (u == e2) {
                units.remove(e2);
                units.add(e4);
            }
        });

        assertThat(units.size()).isEqualTo(3);
        assertThat(units.get(0)).isSameAs(e1);
        assertThat(units.get(1)).isSameAs(e3);
        assertThat(units.get(2)).isSameAs(e4);
    }
}