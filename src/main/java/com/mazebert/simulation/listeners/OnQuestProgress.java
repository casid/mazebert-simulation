package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.quests.Quest;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class OnQuestProgress extends ExposedSignal<OnQuestProgressListener> {

    public void dispatch(Wizard wizard, Quest quest) {
        for (OnQuestProgressListener listener : this) {
            listener.onQuestProgress(wizard, quest);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onQuestProgress(wizard, quest));
        }
    }
}
