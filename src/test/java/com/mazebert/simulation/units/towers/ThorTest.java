package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

class ThorTest extends ItemTest {
    @Override
    protected Tower createTower() {
        return new Thor();
    }

    @Test
    void gold() {
        System.out.println(tower.getGoldCost());

        System.out.println(StrictMath.round(StrictMath.sqrt(810795455L)));
    }

    @Test
    void name() {
        tower.addMaxLevel(300);

        for (int i = 1; i <= 399; i++) {
            tower.setLevel(i);

            System.out.println("Level " + i + ": " + (int)tower.getMinBaseDamage() + "-" + (int)tower.getMaxBaseDamage());
        }
    }

    @Test
    void reminder() {
        // TODO adjust ladder backend, so that whenever Thor is forged, the player gains Mjoelnir, too.
    }
}