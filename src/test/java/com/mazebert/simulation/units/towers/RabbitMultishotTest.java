package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.units.TestTower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RabbitMultishotTest extends SimTest {

    Tower tower;
    RabbitMultishot multishot;

    @BeforeEach
    void setUp() {
        tower = new TestTower();
        multishot = new RabbitMultishot();
        tower.addAbility(multishot);
    }

    @Test
    void initial() {
        assertThat(multishot.getTargets()).isEqualTo(2);
    }

    @Test
    void level1() {
        tower.setLevel(1);
        assertThat(multishot.getTargets()).isEqualTo(2);
    }

    @Test
    void level16() {
        tower.setLevel(16);
        assertThat(multishot.getTargets()).isEqualTo(3);
    }
}