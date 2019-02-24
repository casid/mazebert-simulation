package com.mazebert.simulation.listeners;

import com.mazebert.simulation.tutorial.Lesson;

public strictfp class OnTutorialLessonStarted extends ExposedSignal<OnTutorialLessonStartedListener> {
    public void dispatch(Lesson lesson) {
        dispatchAll(l -> l.onTutorialLessonStarted(lesson));
    }
}
