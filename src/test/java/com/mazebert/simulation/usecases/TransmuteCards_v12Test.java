package com.mazebert.simulation.usecases;

import com.mazebert.simulation.*;
import com.mazebert.simulation.commands.TransmuteCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnCardsTransmutedListener;
import com.mazebert.simulation.plugins.ClientPluginTrainer;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

public class TransmuteCards_v12Test extends UsecaseTest<TransmuteCardsCommand> implements OnCardsTransmutedListener {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;

    CardType result;
    Collection<CardType> results;

    @BeforeEach
    void setUp() {
        version = Sim.v12;

        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        clientPlugin = new ClientPluginTrainer();

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.towerStash.setElements(EnumSet.of(Element.Nature));
        unitGateway.addUnit(wizard);

        usecase = new TransmuteCards();
        request.playerId = 1;
    }

    @Test
    void potion_twoCards_uncommon_angelicElixirOwned() {
        version = Sim.v12;
        randomPluginTrainer.givenFloatAbs(0.0f);
        wizard.foilPotions.add(PotionType.AngelicElixir);
        wizard.potionStash.add(PotionType.UncommonCrit);
        wizard.potionStash.add(PotionType.UncommonCrit);
        request.cardCategory = CardCategory.Potion;
        request.cardType = PotionType.UncommonCrit;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.potionStash.size()).isEqualTo(1);
        assertThat(wizard.potionStash.transmutedUncommons).isEqualTo(0);
        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.AngelicElixir);
    }

    @Test
    void potion_twoCards_uncommon_angelicElixirNotOwned() {
        randomPluginTrainer.givenFloatAbs(0.0f, 0.5f);
        wizard.foilPotions.remove(PotionType.AngelicElixir);
        wizard.potionStash.add(PotionType.UncommonCrit);
        wizard.potionStash.add(PotionType.UncommonCrit);
        request.cardCategory = CardCategory.Potion;
        request.cardType = PotionType.UncommonCrit;
        request.all = true;

        whenRequestIsExecuted();

        assertThat(wizard.potionStash.size()).isEqualTo(1);
        assertThat(wizard.potionStash.transmutedUncommons).isEqualTo(0);
        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(PotionType.RareSpeed);
    }

    @Test
    void potion_angelicElixir() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        wizard.foilPotions.add(PotionType.AngelicElixir);
        wizard.potionStash.add(PotionType.AngelicElixir);
        request.cardCategory = CardCategory.Potion;
        request.cardType = PotionType.AngelicElixir;

        whenRequestIsExecuted();

        assertThat(wizard.potionStash.size()).isEqualTo(0);
        assertThat(wizard.transmutedUniques).isEqualTo(1);
    }

    @Override
    public void onCardTransmuted(Rarity rarity, CardType cardType) {
        result = cardType;
    }

    @Override
    public void onCardsTransmuted(Rarity rarity, Collection<CardType> cardType, int transmutedCards) {
        results = cardType;
    }
}