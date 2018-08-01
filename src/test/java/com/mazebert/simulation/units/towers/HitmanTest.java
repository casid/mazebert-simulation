package com.mazebert.simulation.units.towers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.ComponentTest;

public class HitmanTest implements ComponentTest {
    Hitman tower;

    @BeforeEach
    void setUp() {
         tower = new Hitman();
    }

    @Test
    void settings() {

    }

    @Test
    void cooldownNotReached() {

    }

    @Test
    void cooldownReached() {
        tower.simulate(tower.getCooldown());
    }
}