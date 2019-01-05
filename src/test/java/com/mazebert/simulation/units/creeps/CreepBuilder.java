package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import org.jusecase.builders.Builder;

public class CreepBuilder implements Builder<Creep> {

    private final Creep creep;

    public static CreepBuilder creep() {
        return new CreepBuilder();
    }

    public CreepBuilder() {
        creep = new Creep();
        creep.setWave(new Wave());
    }

    @Override
    public Creep build() {
        return creep;
    }

    public CreepBuilder boss() {
        creep.getWave().type = WaveType.Boss;
        return this;
    }

    public CreepBuilder air() {
        creep.getWave().type = WaveType.Air;
        return this;
    }
}