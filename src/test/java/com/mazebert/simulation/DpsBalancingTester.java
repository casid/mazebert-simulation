package com.mazebert.simulation;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DpsBalancingTester extends ItemTest {
    @Test
    void towers() {
        List<Result> result = new ArrayList<>();

        for (TowerType towerType : TowerType.values()) {
            Tower tower = towerType.create();
            tower.setLevel(99);

            double baseDamage = tower.calculateAverageBaseDamageForDisplay();
            double dps = baseDamage / tower.getCooldown();

            result.add(new Result(tower, dps));
        }

        result.sort(Comparator.comparingDouble(r -> r.dps));
        Collections.reverse(result);

        for (Result r : result) {
            System.out.println(r.tower.getName() + ": " + r.dps);
        }
    }

    static class Result {
        Tower tower;
        double dps;

        public Result(Tower tower, double dps) {
            this.tower = tower;
            this.dps = dps;
        }
    }
}
