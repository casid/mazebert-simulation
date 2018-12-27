package com.mazebert.simulation.usecases;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.TransmuteCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnCardsTransmutedListener;
import com.mazebert.simulation.plugins.ClientPluginTrainer;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class TransmuteCardsTest extends UsecaseTest<TransmuteCardsCommand> implements OnCardsTransmutedListener {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;

    CardType result;
    Collection<CardType> results;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        clientPlugin = new ClientPluginTrainer();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        usecase = new TransmuteCards();
        request.playerId = 1;
    }

    @Test
    void wizardNotFound() {
        request.playerId = 42;
        whenRequestIsExecuted();
        thenNoCardsAreTraded();
    }

    @Test
    void cardNotOwned() {
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Huli;

        whenRequestIsExecuted();

        thenNoCardsAreTraded();
    }

    @Test
    void tower_oneCard() {
        wizard.towerStash.add(TowerType.Dandelion);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Dandelion;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.towerStash.transmutedCommons).isEqualTo(1);
    }

    @Test
    void tower_threeCards() {
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Dandelion);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Dandelion;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.towerStash.transmutedCommons).isEqualTo(3);
    }

    @Test
    void tower_fourCards() {
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Dandelion);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Dandelion;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(1);
        assertThat(wizard.towerStash.transmutedCommons).isEqualTo(0);
        assertThat(wizard.towerStash.get(0).cardType).isEqualTo(TowerType.Frog);
    }

    @Test
    void item_fourCards() {
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.BabySword;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.itemStash.size()).isEqualTo(1);
        assertThat(wizard.itemStash.transmutedCommons).isEqualTo(0);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.Handbag);
    }

    @Test
    void item_fourtyCards() {
        for (int i = 0; i < 40; ++i) {
            wizard.itemStash.add(ItemType.BabySword);
        }
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.BabySword;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.itemStash.size()).isEqualTo(1);
        assertThat(wizard.itemStash.transmutedCommons).isEqualTo(0);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.Handbag);
        assertThat(wizard.itemStash.get(0).amount).isEqualTo(10);
    }

    @Test
    void potion_twoCards_uncommon() {
        wizard.potionStash.add(PotionType.UncommonCrit);
        wizard.potionStash.add(PotionType.UncommonCrit);
        request.cardCategory = CardCategory.Potion;
        request.cardType = PotionType.UncommonCrit;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.potionStash.size()).isEqualTo(1);
        assertThat(wizard.potionStash.transmutedUncommons).isEqualTo(0);
        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.RareDamage);
    }

    @Test
    void tower_fourCards_rare() {
        wizard.towerStash.add(TowerType.Huli);
        wizard.towerStash.add(TowerType.Huli);
        wizard.towerStash.add(TowerType.Huli);
        wizard.towerStash.add(TowerType.Huli);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Huli;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.itemStash.size()).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.KeyOfWisdom);
    }

    @Test
    void tower_oneCard_unique() {
        wizard.towerStash.add(TowerType.KiwiEgg);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.KiwiEgg;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.transmutedUniques).isEqualTo(1);
    }

    @Test
    void unique_oneTower_oneItem() {
        wizard.towerStash.add(TowerType.KiwiEgg);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.KiwiEgg;

        whenRequestIsExecuted();

        wizard.itemStash.add(ItemType.Excalibur);
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.Excalibur;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.itemStash.size()).isEqualTo(0);
        assertThat(wizard.transmutedUniques).isEqualTo(0);
        assertThat(wizard.potionStash.size()).isEqualTo(1);
        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.CardDustCrit);
    }

    @Test
    void tower_jesterKing() {
        wizard.towerStash.add(TowerType.Huli);
        wizard.towerStash.add(TowerType.Huli);
        wizard.towerStash.add(TowerType.Huli);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Huli;
        request.all = true;
        wizard.requiredTransmuteAmount = 3;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.itemStash.size()).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.KeyOfWisdom);
    }

    @Test
    void listener_multipleCards() {
        wizard.onCardsTransmuted.add(this);

        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.BabySword);
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.BabySword;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(results).containsExactly(ItemType.Handbag);
    }

    @Test
    void listener_oneCard() {
        wizard.onCardsTransmuted.add(this);

        wizard.itemStash.transmutedCommons = 3;
        wizard.itemStash.add(ItemType.BabySword);
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.BabySword;

        whenRequestIsExecuted();

        assertThat(result).isEqualTo(ItemType.Handbag);
    }

    @Test
    void newCardIsAddedAtCurrentPosition() {
        wizard.towerStash.transmutedCommons = 3;
        wizard.towerStash.add(TowerType.Beaver);
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Rabbit);

        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Dandelion;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.get(0).cardType).isEqualTo(TowerType.Beaver);
        assertThat(wizard.towerStash.get(1).cardType).isEqualTo(TowerType.Frog);
        assertThat(wizard.towerStash.get(2).cardType).isEqualTo(TowerType.Rabbit);
    }

    @Test
    void newCardIsAddedAtCurrentPosition2() {
        wizard.towerStash.transmutedCommons = 3;
        wizard.towerStash.add(TowerType.Beaver);
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Dandelion);
        wizard.towerStash.add(TowerType.Rabbit);

        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Dandelion;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.get(0).cardType).isEqualTo(TowerType.Beaver);
        assertThat(wizard.towerStash.get(1).cardType).isEqualTo(TowerType.Dandelion);
        assertThat(wizard.towerStash.get(2).cardType).isEqualTo(TowerType.Frog);
        assertThat(wizard.towerStash.get(3).cardType).isEqualTo(TowerType.Rabbit);
    }

    @Test
    void newCardIsAddedAtCurrentPosition_potion() {
        wizard.transmutedUniques = 1;
        wizard.potionStash.add(PotionType.UncommonCrit);
        wizard.potionStash.add(PotionType.Painkiller);
        wizard.potionStash.add(PotionType.RareCrit);

        request.cardCategory = CardCategory.Potion;
        request.cardType = PotionType.Painkiller;

        whenRequestIsExecuted();

        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.UncommonCrit);
        assertThat(wizard.potionStash.get(1).cardType).isEqualTo(PotionType.CardDustCrit);
        assertThat(wizard.potionStash.get(2).cardType).isEqualTo(PotionType.RareCrit);
    }

    @Test
    void item_holger() {
        wizard.itemStash.add(ItemType.BabySword);
        wizard.itemStash.add(ItemType.WoodenStaff);
        wizard.itemStash.add(ItemType.Handbag);
        wizard.itemStash.add(ItemType.RingOfGreed);
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.RingOfGreed;
        wizard.itemStash.transmutedCommons = 3;

        whenRequestIsExecuted();

        assertThat(wizard.itemStash.size()).isEqualTo(3);
        assertThat(wizard.itemStash.transmutedCommons).isEqualTo(0);
        assertThat(wizard.itemStash.get(2).cardType).isEqualTo(ItemType.Handbag);
    }

    private void thenNoCardsAreTraded() {
        assertThat(wizard.towerStash.transmutedCommons).isEqualTo(0);
    }

    @Override
    public void onCardTransmuted(Rarity rarity, CardType cardType) {
        result = cardType;
    }

    @Override
    public void onCardsTransmuted(Rarity rarity, Collection<CardType> cardType) {
        results = cardType;
    }
}