package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepBuilder;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class KillChallengesQuestTest extends ItemTest {
    KillChallengesQuest quest;


    @BeforeEach
    void setUp() {
        quest = new KillChallengesQuest();
        wizard.addAbility(quest);
    }

    @Test
    void challengeKilled() {
        Creep creep = a(creep().challenge());
        unitGateway.addUnit(creep);

        tower.kill(creep);
        unitGateway.removeUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void challengeNotQuiteKilled() {
        Creep creep = a(creep().challenge());
        unitGateway.addUnit(creep);

        creep.setHealth(0.5f);
        unitGateway.removeUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
    }

    @Test
    void regularCreepKilled() {
        Creep creep = a(creep().boss());
        unitGateway.addUnit(creep);

        tower.kill(creep);
        unitGateway.removeUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
    }

    @Test
    void challengeOfOtherWizardKilled() {
        Creep creep = a(creep().challenge());
        creep.setWizard(new Wizard());
        unitGateway.addUnit(creep);

        tower.kill(creep);
        unitGateway.removeUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(0);
    }

    @Test
    void questFullfilled() {
        quest.addAmount(quest.getRequiredAmount() - 1);

        Creep creep = a(creep().challenge());
        unitGateway.addUnit(creep);

        tower.kill(creep);
        unitGateway.removeUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(quest.getRequiredAmount());
    }

    CreepBuilder creep() {
        return CreepBuilder.creep().withWizard(wizard);
    }
}