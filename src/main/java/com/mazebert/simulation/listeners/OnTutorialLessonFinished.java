package com.mazebert.simulation.listeners;

import com.mazebert.simulation.tutorial.Lesson;

public strictfp class OnTutorialLessonFinished extends ExposedSignal<OnTutorialLessonFinishedListener> {
    public void dispatch(Lesson lesson) {
        dispatchAll(l -> l.onTutorialLessonFinished(lesson));
    }
}
