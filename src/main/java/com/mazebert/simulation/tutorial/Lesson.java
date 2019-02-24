package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.PauseCommand;
import com.mazebert.simulation.listeners.OnTutorialLessonFinished;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp abstract class Lesson {
    public final OnTutorialLessonFinished onFinished = new OnTutorialLessonFinished();

    protected final FormatPlugin format = Sim.context().formatPlugin;

    private boolean finished;

    public void initialize(Wizard wizard) {

    }

    public void dispose(Wizard wizard) {
        onFinished.removeAll();
    }

    public void finish() {
        if (!finished) {
            this.finished = true;
            onFinished.dispatch(this);
        }
    }

    protected void pauseGame() {
        pauseGame(true);
    }

    protected void resumeGame() {
        pauseGame(false);
    }

    private void pauseGame(boolean pause) {
        PauseCommand command = new PauseCommand();
        command.pause = pause;

        Sim.context().commandExecutor.executeVoid(command);
    }
}
