package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.plugins.FormatPlugin;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OnlyDarknessQuestTest extends SimTest {
    @Test
    void hidden() {
        OnlyDarknessQuest quest = new OnlyDarknessQuest();
        assertThat(quest.isHidden()).isTrue();
    }

    @Test
    void reward() {
        OnlyDarknessQuest quest = new OnlyDarknessQuest();
        assertThat(quest.reward).isEqualTo(QuestReward.Big.relics);
    }

    @Test
    void description() {
        formatPlugin = new FormatPlugin();
        OnlyDarknessQuest quest = new OnlyDarknessQuest();
        assertThat(quest.getDescription()).isEqualTo("Win a game with <c=#444444>Darkness</c> towers only.");
    }
}