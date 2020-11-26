package com.mazebert.simulation.usecases;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.commands.TransferCardCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.SeelenreisserAbility;
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
    void weddingRing1_cannotBeTransferred() {
        wizard1.itemStash.add(ItemType.WeddingRing1);
        request.cardType = ItemType.WeddingRing1;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.WeddingRing1)).isNotNull();
        assertThat(wizard2.itemStash.get(ItemType.WeddingRing1)).isNull();
    }

    @Test
    void weddingRing2_cannotBeTransferred() {
        wizard1.itemStash.add(ItemType.WeddingRing2);
        request.cardType = ItemType.WeddingRing2;

        whenRequestIsExecuted();

        assertThat(wizard1.itemStash.get(ItemType.WeddingRing2)).isNotNull();
        assertThat(wizard2.itemStash.get(ItemType.WeddingRing2)).isNull();
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
    void name() {
        // TODO test towers
        // TODO test potions
        // TODO test weird uniques / legendaries
    }
}