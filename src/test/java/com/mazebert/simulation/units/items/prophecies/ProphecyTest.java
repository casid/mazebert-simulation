package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Veleda;

public class ProphecyTest extends ItemTest {

    Veleda veleda;

    @Override
    protected void initVersion() {
        version = Sim.vRnR;
        season = true;
    }

    @Override
    protected Tower createTower() {
        return veleda = new Veleda();
    }
}
