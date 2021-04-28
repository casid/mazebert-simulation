package com.mazebert.simulation.usecases;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.commands.TransferCardCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.items.BloodDemonBladeAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.SeelenreisserAbility;
import com.mazebert.simulation.units.quests.TransferCardQuest;
import com.mazebert.simulation.units.quests.TransferUniqueCardQuest;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class TransferCardTest extends UsecaseTest<TransferCardCommand> {

    SimulationListenersTrainer simulationListenersTrainer;

    Wizard wizard1;
    Wizard wizard2;

    @BeforeEach
    void setUp() {
        simulationListenersTrainer = new SimulationListenersTrainer();
        simulationListeners = simulationListenersTrainer;
        unitGateway = new UnitGateway();
        formatPlugin = new FormatPlugin();

        usecase = new TransferCard();

        wizard1 = new Wizard();
        wizard1.name = "Player 1";
        wizard1.playerId = 1;
        unitGateway.addUnit(wizard1);

        wizard2 = new Wizard();
        wizard2.name = "Player 2";
        wizard2.playerId = 2;
        unitGateway.addUnit(wizard2);

        request.playerId = 1;
        request.toPlayerId = 2;
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.BabySword;

        wizard1.itemStash.add(ItemType.BabySword);
    }

    @Test
    void success() {
        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.BabySword)).isNull();
        assertThat(wizard2.itemStash.get(ItemType.BabySword)).isNotNull();
        String notification = "<c=#ff0000>Player 1</c> transferred <c=#fefefe>Baby Sword</c> to <c=#0000ff>Player 2</c>.";
        simulationListenersTrainer.thenNotificationsAre(wizard1, notification);
        simulationListenersTrainer.thenNotificationsAre(wizard2, notification);
    }

    @Test
    void itemNotInPossession() {
        wizard1.itemStash.remove(ItemType.BabySword);

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.BabySword)).isNull();
        assertThat(wizard2.itemStash.get(ItemType.BabySword)).isNull();
    }

    @Test
    void itemOwnedMultipleTimes() {
        wizard1.itemStash.add(ItemType.BabySword);
        wizard2.itemStash.add(ItemType.BabySword);

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.BabySword).amount).isEqualTo(1);
        assertThat(wizard2.itemStash.get(ItemType.BabySword).amount).isEqualTo(2);
    }

    @Test
    void uniqueItem() {
        wizard1.itemStash.add(ItemType.Excalibur);
        request.cardType = ItemType.Excalibur;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.Excalibur)).isNull();
        assertThat(wizard2.itemStash.get(ItemType.Excalibur).amount).isEqualTo(1);
    }

    @Test
    void uniqueItem_alreadyDropped() {
        wizard1.itemStash.add(ItemType.Excalibur);
        wizard2.itemStash.add(ItemType.Excalibur);
        request.cardType = ItemType.Excalibur;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.Excalibur).amount).isEqualTo(1);
        assertThat(wizard2.itemStash.get(ItemType.Excalibur).amount).isEqualTo(1);
    }

    @Test
    void uniqueItem_withStats() {
        wizard1.itemStash.add(ItemType.Seelenreisser);
        Item seelenreisser1 = wizard1.itemStash.get(ItemType.Seelenreisser).card;
        SeelenreisserAbility seelenreisserAbility1 = seelenreisser1.getAbility(SeelenreisserAbility.class);
        seelenreisserAbility1.onKill(a(creep()));

        request.cardType = ItemType.Seelenreisser;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.Seelenreisser)).isNull();
        assertThat(wizard2.itemStash.get(ItemType.Seelenreisser).amount).isEqualTo(1);
        Item seelenreisser2 = wizard2.itemStash.get(ItemType.Seelenreisser).card;
        SeelenreisserAbility seelenreisserAbility2 = seelenreisser2.getAbility(SeelenreisserAbility.class);
        assertThat(seelenreisserAbility2.getTotalDamage()).isEqualTo(0.01f);
    }

    @Test
    void bloodDemonBlade() {
        wizard1.itemStash.add(ItemType.BloodDemonBlade);
        Item blade = wizard1.itemStash.get(ItemType.BloodDemonBlade).card;
        BloodDemonBladeAbility bladeAbility1 = blade.getAbility(BloodDemonBladeAbility.class);
        bladeAbility1.setDamage(1000);

        request.cardType = ItemType.BloodDemonBlade;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.BloodDemonBlade)).isNull();
        assertThat(wizard2.itemStash.get(ItemType.BloodDemonBlade).amount).isEqualTo(1);
        Item blade2 = wizard2.itemStash.get(ItemType.BloodDemonBlade).card;
        BloodDemonBladeAbility bladeAbility2 = blade2.getAbility(BloodDemonBladeAbility.class);
        assertThat(bladeAbility2.getDamage()).isEqualTo(1000);
    }

    @Test
    void weddingRing1_cannotBeTransferred() {
        assertItemCannotBeTransferred(ItemType.WeddingRing1);
    }

    @Test
    void weddingRing2_cannotBeTransferred() {
        assertItemCannotBeTransferred(ItemType.WeddingRing2);
    }

    @Test
    void branchesOfYggdrasil_cannotBeTransferred() {
        assertItemCannotBeTransferred(ItemType.BranchOfYggdrasilNature);
        assertItemCannotBeTransferred(ItemType.BranchOfYggdrasilMetropolis);
        assertItemCannotBeTransferred(ItemType.BranchOfYggdrasilDarkness);
        assertItemCannotBeTransferred(ItemType.BranchOfYggdrasilLight);
    }

    @Test
    void tutorialCard_notAllowed() {
        wizard1.itemStash.add(ItemType.TransmuteStack);
        request.cardType = ItemType.TransmuteStack;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.TransmuteStack)).isNotNull();
        assertThat(wizard2.itemStash.get(ItemType.TransmuteStack)).isNull();
    }

    @Test
    void sameWizard() {
        request.playerId = 1;
        request.toPlayerId = 1;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.BabySword)).isNotNull();
        simulationListenersTrainer.thenNoNotificationsWereShown(wizard1);
    }

    @Test
    void questProgress() {
        TransferCardQuest quest = new TransferCardQuest();
        wizard1.addAbility(quest);

        whenRequestIsExecuted();

        assertThat(quest.isComplete()).isTrue();
    }

    @Test
    void uniqueQuestProgress_regularItem() {
        TransferUniqueCardQuest quest = new TransferUniqueCardQuest();
        wizard1.addAbility(quest);

        whenRequestIsExecuted();

        assertThat(quest.isComplete()).isFalse();
    }

    @Test
    void uniqueQuestProgress_uniqueItem() {
        TransferUniqueCardQuest quest = new TransferUniqueCardQuest();
        wizard1.addAbility(quest);
        wizard1.itemStash.add(ItemType.Excalibur);
        request.cardType = ItemType.Excalibur;

        whenRequestIsExecuted();

        assertThat(quest.isComplete()).isTrue();
    }

    @Test
    void noTransfersLeft() {
        wizard1.remainingCardTransfers = 0;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.BabySword).amount).isEqualTo(1);
        assertThat(wizard2.itemStash.get(ItemType.BabySword)).isNull();
    }

    @Test
    void transfersAreUsed() {
        wizard1.remainingCardTransfers = 7;

        whenRequestIsExecuted();

        assertThat(wizard1.remainingCardTransfers).isEqualTo(6);
    }

    private void assertItemCannotBeTransferred(ItemType itemType) {
        wizard1.itemStash.add(itemType);
        request.cardType = itemType;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(itemType)).isNotNull();
        assertThat(wizard2.itemStash.get(itemType)).isNull();
    }
}