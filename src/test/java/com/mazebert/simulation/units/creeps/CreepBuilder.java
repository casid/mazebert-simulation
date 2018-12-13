package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Wave;
import org.jusecase.builders.Builder;

public class CreepBuilder implements Builder<Creep> {

    private final Creep creep;

    public CreepBuilder() {
        creep = new Creep();
        creep.setWave(new Wave());
    }

    @Override
    public Creep build() {
        return creep;
    }

    public static CreepBuilder creep() {
        return new CreepBuilder();
    }
}