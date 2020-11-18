package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.jusecase.builders.Builder;

public class CreepBuilder implements Builder<Creep> {

    private final Creep creep;

    public static CreepBuilder creep() {
        return new CreepBuilder();
    }

    public CreepBuilder() {
        creep = new Creep();
        creep.setWave(new Wave());
        creep.setExperience(2);
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

    public CreepBuilder challenge() {
        creep.getWave().type = WaveType.Challenge;
        return this;
    }

    public CreepBuilder cultist() {
        creep.getWave().type = WaveType.CultistOfDagon;
        return this;
    }

    public CreepBuilder horseman() {
        creep.getWave().type = WaveType.Horseman;
        return this;
    }

    public CreepBuilder withWizard(Wizard wizard) {
        creep.setWizard(wizard);
        return this;
    }

    public CreepBuilder mass() {
        creep.getWave().type = WaveType.Mass;
        return this;
    }

    public CreepBuilder massChallenge() {
        creep.getWave().type = WaveType.MassChallenge;
        return this;
    }

    public CreepBuilder timeLord() {
        creep.getWave().type = WaveType.TimeLord;
        return this;
    }

    public CreepBuilder dead() {
        creep.setHealth(0);
        return this;
    }
}