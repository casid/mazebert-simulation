package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import org.junit.jupiter.api.Test;

public class HeroTypeToSqlTester extends SimTest {
    @Test
    void export() {
        simulationListeners = new SimulationListeners();
        season = true;

        for (HeroType heroType : HeroType.getValues()) {
            String name = heroType.instance().getName().replace("'", "");
            System.out.println("insert into Hero(id, name) values(" + heroType.id + ", '" + name + "');");
        }
    }
}