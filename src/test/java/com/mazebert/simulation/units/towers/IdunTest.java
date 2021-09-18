package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

class IdunTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new Idun();
    }

    @Test
    void name() {
        // TODO creeps that die in range are revived
        // TODO max level of nature towers in range is equal to your current health.
    }
}