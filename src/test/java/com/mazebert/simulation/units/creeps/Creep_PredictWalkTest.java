package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.maps.FollowPathResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class Creep_PredictWalkTest {
    Creep creep = new Creep();
    Path path = new Path(0, 0, 0, 1);
    FollowPathResult followPathResult = new FollowPathResult();

    @BeforeEach
    void setUp() {
        creep.setPath(path);
    }

    @Test
    void backwardClamps() {
        creep.warpInTime(-1);
        followPathResult.pathIndex = -1;
        followPathResult = creep.predictWalk(0.1f, 0, 0.01f, followPathResult);

        assertThat(creep.getX()).isEqualTo(0);
        assertThat(creep.getY()).isEqualTo(0);
    }

    @Test
    void forwardClamps() {
        creep.warpInTime(1);
        followPathResult.pathIndex = -1;
        followPathResult = creep.predictWalk(0.1f, 0, 0.01f, followPathResult);
    }

    @Test
    void defensivePrediction() {
        followPathResult.pathIndex = -2;
        followPathResult = creep.predictWalk(0.1f, 0, 0.01f, followPathResult);
    }

    @Test
    void backwardEdgeCase() {
        path = new Path(0, 0, 0, 10);
        creep.predictWalk(0, 3, -3, followPathResult);
        assertThat(followPathResult.pathIndex).isEqualTo(0);
    }
}