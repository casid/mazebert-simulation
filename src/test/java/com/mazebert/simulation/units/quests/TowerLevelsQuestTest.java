package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TowerLevelsQuestTest extends ItemTest {

    private TowerLevelsQuest quest;

    @BeforeEach
    void setUp() {
        tower.setLevel(1);

        quest = new TowerLevelsQuest();
        wizard.addAbility(quest);
    }

    @Test
    void oneLevelUp() {
        tower.setLevel(2);
        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void tenLevelsUp() {
        tower.setLevel(11);
        assertThat(quest.getCurrentAmount()).isEqualTo(10);
    }

    @Test
    void oneLevelDown_ignored() {
        tower.setLevel(2);
        tower.setLevel(1);

        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void towerIsReplaced() {
        Tower dandelion = whenTowerIsReplaced(this.tower, TowerType.Dandelion);
        dandelion.setLevel(11);

        assertThat(quest.getCurrentAmount()).isEqualTo(10);
    }
}