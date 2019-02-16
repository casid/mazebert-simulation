package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.quests.Quest;
import com.mazebert.simulation.units.wizards.Wizard;

public interface OnQuestProgressListener {
    void onQuestProgress(Wizard wizard, Quest quest);
}
