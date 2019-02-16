package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.towers.Hitman;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class OnlyDarknessAndNatureQuestTest extends ItemTest {

    private OnlyDarknessAndNatureQuest quest;

    @BeforeEach
    void setUp() {
        quest = new OnlyDarknessAndNatureQuest();
        wizard.addAbility(quest);
    }

    @Test
    void wrongElementBuilt() {
        whenTowerIsReplaced(tower, TowerType.Hitman);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
        assertThat(wizard.getAbility(OnlyDarknessAndNatureQuest.class)).isNull();
    }

    @Test
    void allowedElementBuilt1() {
        whenTowerIsReplaced(tower, TowerType.Dandelion);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
        assertThat(wizard.getAbility(OnlyDarknessAndNatureQuest.class)).isNotNull();
    }

    @Test
    void allowedElementBuilt2() {
        whenTowerIsReplaced(tower, TowerType.Spider);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
        assertThat(wizard.getAbility(OnlyDarknessAndNatureQuest.class)).isNotNull();
    }

    @Test
    void towerOfOtherWizardIsIgnored() {
        Hitman hitman = new Hitman();
        hitman.setWizard(new Wizard());
        unitGateway.addUnit(hitman);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
        assertThat(wizard.getAbility(OnlyDarknessAndNatureQuest.class)).isNotNull();
    }

    @Test
    void creepAddedIsIgnored() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
        assertThat(wizard.getAbility(OnlyDarknessAndNatureQuest.class)).isNotNull();
    }

    @Test
    void gameIsWon() {
        simulationListeners.onGameWon.dispatch();
        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }
}