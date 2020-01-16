package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import org.junit.jupiter.api.Test;

public class TowerTypeToSqlTester extends SimTest {
    @Test
    void export() {
        simulationListeners = new SimulationListeners();
        season = true;

        for (TowerType towerType : TowerType.getValues()) {
            String name = towerType.instance().getName();
            if ("Lucifer".equals(name)) {
                name += " (" + towerType.instance().getElement() + ")";
            }
            System.out.println("insert into Tower(id, name) values(" + towerType.id + ", '" + name + "');");
        }
    }
}