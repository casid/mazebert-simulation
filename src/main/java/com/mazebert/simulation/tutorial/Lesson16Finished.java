package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson16Finished extends Lesson07SecondWave {
    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        pauseGame();
    }
}
