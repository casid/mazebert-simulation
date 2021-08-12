package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Veleda;

public class ProphecyTest extends ItemTest {

    Veleda veleda;

    @Override
    protected Tower createTower() {
        return veleda = new Veleda();
    }
}
