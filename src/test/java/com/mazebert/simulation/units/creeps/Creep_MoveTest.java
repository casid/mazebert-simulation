package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Creep_MoveTest {

    Creep creep;

    @BeforeEach
    void setUp() {
        creep = new Creep();
        creep.setBaseSpeed(1.0f);
    }

    @Test
    void moveX() {
        creep.setPath(new Path(0.0f, 0.0f, 1.0f, 0.0f));
        creep.simulate(1.0f);
        assertThat(creep.getX()).isEqualTo(1.0f);
        assertThat(creep.getY()).isEqualTo(0.0f);
    }

    @Test
    void moveY() {
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));
        creep.simulate(1.0f);
        assertThat(creep.getX()).isEqualTo(0.0f);
        assertThat(creep.getY()).isEqualTo(1.0f);
    }

    @Test
    void moveDiagonal() {
        creep.setPath(new Path(0.0f, 0.0f, 1.0f, 1.0f));
        creep.simulate(1.0f);
        assertThat(creep.getX()).isEqualTo((float)StrictMath.sqrt(0.5f));
        assertThat(creep.getY()).isEqualTo((float)StrictMath.sqrt(0.5f));
    }

    @Test
    void pathTargetReached() {
        creep.setPath(new Path(0.0f, 0.0f, 1.0f, 1.0f));

        creep.simulate(1.0f);
        creep.simulate(1.0f);

        assertThat(creep.getX()).isEqualTo(1.0f);
        assertThat(creep.getY()).isEqualTo(1.0f);
        // TODO onTargetReached fired
    }

    @Test
    void moveFurther() {
        creep.setPath(new Path(0.0f, 0.0f, 0.0f, 1.5f, 1.0f, 1.5f));

        creep.simulate(1.0f);
        creep.simulate(1.0f);

        assertThat(creep.getX()).isEqualTo(0.5f);
        assertThat(creep.getY()).isEqualTo(1.5f);
    }

    @Test
    void death() {
        creep.setPath(new Path(0.0f, 0.0f, 1.0f, 0.0f));
        creep.setState(CreepState.Death);

        creep.simulate(1.0f);

        assertThat(creep.getX()).isEqualTo(0.0f);
        assertThat(creep.getY()).isEqualTo(0.0f);
    }

    @Test
    void hit() {
        creep.setPath(new Path(0.0f, 0.0f, 1.0f, 0.0f));
        creep.setState(CreepState.Hit);

        creep.simulate(1.0f);

        assertThat(creep.getX()).isEqualTo(0.0f);
        assertThat(creep.getY()).isEqualTo(0.0f);
    }
}