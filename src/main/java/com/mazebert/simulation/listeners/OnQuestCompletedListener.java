package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.quests.Quest;
import com.mazebert.simulation.units.wizards.Wizard;

public interface OnQuestCompletedListener {
    void onQuestCompleted(Wizard wizard, Quest quest);
}
