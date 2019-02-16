package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.quests.Quest;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class OnQuestCompleted extends ExposedSignal<OnQuestCompletedListener> {

    public void dispatch(Wizard wizard, Quest quest) {
        for (OnQuestCompletedListener listener : this) {
            listener.onQuestCompleted(wizard, quest);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onQuestCompleted(wizard, quest));
        }
    }
}
