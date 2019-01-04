package com.mazebert.simulation.maps;

import com.mazebert.simulation.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class FollowPath_FindClosestPointTest {
    @Test
    void test1() {
        Path path = new Path(0, 0, 0, 10);

        FollowPathResult result = FollowPath.findClosestPointOnPath(2, 5, path);

        assertThat(result.px).isEqualTo(0);
        assertThat(result.py).isEqualTo(5);
    }

    @Test
    void test2() {
        Path path = new Path(0, 0, 0, 10);

        FollowPathResult result = FollowPath.findClosestPointOnPath(-13, 5, path);

        assertThat(result.px).isEqualTo(0);
        assertThat(result.py).isEqualTo(5);
    }

    @Test
    void test3() {
        Path path = new Path(0, 0, 10, 0);

        FollowPathResult result = FollowPath.findClosestPointOnPath(3, 7, path);

        assertThat(result.px).isEqualTo(3);
        assertThat(result.py).isEqualTo(0);
    }

    @Test
    void test4() {
        Path path = new Path(0, 0, 10, 0);

        FollowPathResult result = FollowPath.findClosestPointOnPath(3, -7, path);

        assertThat(result.px).isEqualTo(3);
        assertThat(result.py).isEqualTo(0);
    }

    @Test
    void test5() {
        Path path = new Path(-5, 2, 5, 0);

        FollowPathResult result = FollowPath.findClosestPointOnPath(1, 3, path);

        assertThat(result.px).isEqualTo(0.5769231f);
        assertThat(result.py).isEqualTo(0.8846152f);
    }

    @Test
    void testOutOfRange1() {
        Path path = new Path(0, 0, 0, 10);

        FollowPathResult result = FollowPath.findClosestPointOnPath(-2, 11, path);

        assertThat(result.px).isEqualTo(0);
        assertThat(result.py).isEqualTo(10);
    }

    @Test
    void testOutOfRange2() {
        Path path = new Path(0, 0, 0, 10);

        FollowPathResult result = FollowPath.findClosestPointOnPath(-2, -1, path);

        assertThat(result.px).isEqualTo(0);
        assertThat(result.py).isEqualTo(0);
    }

    @Test
    void testLongerPath1() {
        Path path = new Path(0, 0, 0, 10, 10, 10);

        FollowPathResult result = FollowPath.findClosestPointOnPath(5, 8, path);

        assertThat(result.pathIndex).isEqualTo(1);
        assertThat(result.px).isEqualTo(5);
        assertThat(result.py).isEqualTo(10);
    }

    @Test
    void testLongerPath2() {
        Path path = new Path(0, 0, 0, 10, 10, 10);

        FollowPathResult result = FollowPath.findClosestPointOnPath(1, 1, path);

        assertThat(result.pathIndex).isEqualTo(0);
        assertThat(result.px).isEqualTo(0);
        assertThat(result.py).isEqualTo(1);
    }
}