package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepBuilder;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class KillCultistsQuestTest extends ItemTest {
    KillCultistsOfDagonQuest quest;


    @BeforeEach
    void setUp() {
        quest = new KillCultistsOfDagonQuest();
        wizard.addAbility(quest);
    }

    @Test
    void cultistKilled() {
        Creep creep = a(creep().cultist());
        unitGateway.addUnit(creep);

        tower.kill(creep);
        unitGateway.removeUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void cultistNotQuiteKilled() {
        Creep creep = a(creep().cultist());
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
    void questFullfilled() {
        quest.addAmount(quest.getRequiredAmount() - 1);

        Creep creep = a(creep().cultist());
        unitGateway.addUnit(creep);

        tower.kill(creep);
        unitGateway.removeUnit(creep);

        assertThat(quest.getCurrentAmount()).isEqualTo(quest.getRequiredAmount());
        assertThat(wizard.getAbility(KillCultistsOfDagonQuest.class)).isNull();
    }

    CreepBuilder creep() {
        return CreepBuilder.creep().withWizard(wizard);
    }
}