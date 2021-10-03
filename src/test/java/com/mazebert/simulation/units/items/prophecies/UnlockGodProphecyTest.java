package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.quests.UnlockThorQuest;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class UnlockGodProphecyTest extends ProphecyTest {
    @Test
    void challengeKilled() {
        UnlockThorQuest quest = new UnlockThorQuest();
        wizard.addAbility(quest);

        whenItemIsEquipped(ItemType.UnlockThorProphecy);

        whenChallengeIsKilled();

        assertThat(quest.getCurrentAmount()).isEqualTo(1);
    }

    @Test
    void challengeKilledToCompleteQuest() {
        UnlockThorQuest quest = new UnlockThorQuest();
        quest.setCurrentAmount(99);
        wizard.addAbility(quest);

        whenItemIsEquipped(ItemType.UnlockThorProphecy);

        whenChallengeIsKilled();

        assertThat(quest.isComplete()).isTrue();
        assertThat(quest.getCurrentAmount()).isEqualTo(100);
        assertThat(wizard.towerStash.get(0).cardType).isEqualTo(TowerType.Thor);
        assertThat(wizard.towerStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.ownsFoilCard(TowerType.Thor)).isTrue();
        assertThat(wizard.ownsFoilCard(ItemType.Mjoelnir)).isTrue();
    }

    private void whenChallengeIsKilled() {
        Creep challenge = a(creep().challenge());
        unitGateway.addUnit(challenge);
        veleda.kill(challenge);
        unitGateway.removeUnit(challenge);
    }
}