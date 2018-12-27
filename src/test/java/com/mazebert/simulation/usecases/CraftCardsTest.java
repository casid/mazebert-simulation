package com.mazebert.simulation.usecases;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.CraftCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnCardsCraftedListener;
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

public class CraftCardsTest extends UsecaseTest<CraftCardsCommand> implements OnCardsCraftedListener {

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

        usecase = new CraftCards();
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
        assertThat(wizard.towerStash.craftedCommons).isEqualTo(1);
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
        assertThat(wizard.towerStash.craftedCommons).isEqualTo(3);
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
        assertThat(wizard.towerStash.craftedCommons).isEqualTo(0);
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
        assertThat(wizard.itemStash.craftedCommons).isEqualTo(0);
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
        assertThat(wizard.itemStash.craftedCommons).isEqualTo(0);
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
        assertThat(wizard.potionStash.craftedUncommons).isEqualTo(0);
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
        assertThat(wizard.craftedUniques).isEqualTo(1);
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
        assertThat(wizard.craftedUniques).isEqualTo(0);
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
        wizard.requiredCraftAmount = 3;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.itemStash.size()).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.KeyOfWisdom);
    }

    @Test
    void listener_multipleCards() {
        wizard.onCardsCrafted.add(this);

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
        wizard.onCardsCrafted.add(this);

        wizard.itemStash.craftedCommons = 3;
        wizard.itemStash.add(ItemType.BabySword);
        request.cardCategory = CardCategory.Item;
        request.cardType = ItemType.BabySword;

        whenRequestIsExecuted();

        assertThat(result).isEqualTo(ItemType.Handbag);
    }

    private void thenNoCardsAreTraded() {
        assertThat(wizard.towerStash.craftedCommons).isEqualTo(0);
    }

    @Override
    public void onCardCrafted(CardType cardType) {
        result = cardType;
    }

    @Override
    public void onCardsCrafted(Collection<CardType> cardType) {
        results = cardType;
    }
}