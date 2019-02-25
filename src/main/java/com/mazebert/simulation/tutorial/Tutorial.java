package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnTutorialLessonFinished;
import com.mazebert.simulation.listeners.OnTutorialLessonFinishedListener;
import com.mazebert.simulation.listeners.OnTutorialLessonStarted;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

public strictfp class Tutorial implements OnTutorialLessonFinishedListener {
    public final OnTutorialLessonStarted onLessonStarted = new OnTutorialLessonStarted();
    public final OnTutorialLessonFinished onLessonFinished = new OnTutorialLessonFinished();

    private final List<Lesson> lessons = new ArrayList<>();

    private Wizard wizard;
    private Lesson currentLesson;
    private int currentLessonIndex;

    public Tutorial() {
        lessons.add(new Lesson01PauseGame());
        lessons.add(new Lesson02BuildTower());
        lessons.add(new Lesson03FirstWave());
        lessons.add(new Lesson04KillCreeps());
        lessons.add(new Lesson05EquipItem());
        lessons.add(new Lesson06BuildTower());
    }

    public void dispose() {
        lessons.clear();
    }

    public void start(Wizard wizard) {
        this.wizard = wizard;
        startLesson();
    }

    private void startLesson() {
        currentLesson = lessons.get(currentLessonIndex);
        currentLesson.initialize(wizard);
        currentLesson.onFinished.add(this);

        onLessonStarted.dispatch(currentLesson);
    }


    private void startNextLesson() {
        currentLesson.dispose(wizard);
        onLessonFinished.dispatch(currentLesson);

        ++currentLessonIndex;
        if (!isFinished()) {
            startLesson();
        }
    }

    public boolean isFinished() {
        return currentLessonIndex >= lessons.size();
    }

    @SuppressWarnings("unused") // Used by client
    public Lesson getCurrentLesson() {
        return currentLesson;
    }

    @Override
    public void onTutorialLessonFinished(Lesson lesson) {
        startNextLesson();
        if (isFinished()) {
            Sim.context().gameSystem.endTutorial();
        }
    }
}
