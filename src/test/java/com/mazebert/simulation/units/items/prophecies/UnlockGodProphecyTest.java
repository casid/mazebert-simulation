package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.quests.UnlockThorQuest;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class UnlockGodProphecyTest extends ProphecyTest {

    private static final float UNLOCK_GOD_PROPHECY_ROLL = 0.99f;

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

    @Test
    void thorProphecyDrop() {
        wizard.towerStash.setElements(EnumSet.of(Element.Light));
        veleda.setLevel(13);
        randomPluginTrainer.givenFloatAbs(0.0f, UNLOCK_GOD_PROPHECY_ROLL);

        simulationListeners.onRoundStarted.dispatch(null);

        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.UnlockThorProphecy);
    }

    @Test
    void lokiProphecyDrop() {
        wizard.towerStash.setElements(EnumSet.of(Element.Metropolis));
        veleda.setLevel(13);
        randomPluginTrainer.givenFloatAbs(0.0f, UNLOCK_GOD_PROPHECY_ROLL);

        simulationListeners.onRoundStarted.dispatch(null);

        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.UnlockLokiProphecy);
    }

    private void whenChallengeIsKilled() {
        Creep challenge = a(creep().challenge());
        unitGateway.addUnit(challenge);
        veleda.kill(challenge);
        unitGateway.removeUnit(challenge);
    }
}