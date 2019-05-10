package com.mazebert.simulation.usecases;

import com.mazebert.simulation.*;
import com.mazebert.simulation.commands.TransmuteCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.stash.PotionStash;
import com.mazebert.simulation.stash.ReadonlyStash;
import com.mazebert.simulation.stash.Stash;
import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

public strictfp class TransmuteCards extends Usecase<TransmuteCardsCommand> {

    private static final int requiredUniqueAmount = 2;

    private static final PotionType[] cardDusts = {
            PotionType.CardDustCrit,
            PotionType.CardDustLuck,
            PotionType.CardDustLevel,
            PotionType.CardDustVital
    };

    public static float getProgress(Wizard wizard, ReadonlyStash stash, Rarity rarity) {
        switch (rarity) {
            case Common:
                return (float)stash.getTransmutedCommons() / getRequiredAmount(wizard, stash);
            case Uncommon:
                return (float)stash.getTransmutedUncommons() / getRequiredAmount(wizard, stash);
            case Rare:
                return (float)stash.getTransmutedRares() / getRequiredAmount(wizard, stash);
        }

        return (float)wizard.transmutedUniques / requiredUniqueAmount;
    }

    private static int getRequiredAmount(Wizard wizard, ReadonlyStash stash) {
        if (stash instanceof PotionStash) {
            return requiredUniqueAmount;
        }
        return wizard.requiredTransmuteAmount;
    }

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

    @Override
    public void execute(TransmuteCardsCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        Stash stash = getStash(wizard, command.cardCategory);
        if (stash == null) {
            return;
        }

        if (!command.cardType.instance().isTradingAllowed()) {
            return;
        }

        if (command.all) {
            transmuteAll(wizard, stash, command.cardType);
        } else {
            if (command.cardType == ItemType.TransmuteStack) {
                return;
            }
            transmute(wizard, stash, command.cardType);
        }
    }

    @SuppressWarnings("unchecked")
    public void transmuteAll(Wizard wizard, Stash stash, CardType cardType) {
        List<CardType> result = null;
        int transmutedCards = 0;

        int index = stash.getIndex(cardType);

        Card card;
        while ((card = stash.remove(cardType, false)) != null) {
            CardType drop = transmute(wizard, stash, card, cardType, index);
            if (drop != null) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(drop);
            }
            ++transmutedCards;
        }

        Rarity rarity = cardType.instance().getRarity();

        wizard.onCardsTransmuted.dispatch(rarity, result, transmutedCards);
    }

    @SuppressWarnings("unchecked")
    public void transmute(Wizard wizard, Stash stash, CardType cardType) {
        int index = stash.getIndex(cardType);

        Card card = stash.remove(cardType, false);
        if (card == null) {
            return;
        }

        Rarity rarity = cardType.instance().getRarity();

        CardType drop = transmute(wizard, stash, card, cardType, index);
        wizard.onCardsTransmuted.dispatch(rarity, drop);
    }

    private CardType transmute(Wizard wizard, Stash stash, Card card, CardType cardType, int index) {
        switch (card.getRarity()) {
            case Common:
                return transmuteCommon(wizard, stash, cardType, index);
            case Uncommon:
                return transmuteUncommon(wizard, stash, cardType, index);
            case Rare:
                return transmuteRare(wizard, stash);
            case Unique:
            case Legendary:
                return transmuteUnique(wizard, stash, cardType, index);
        }

        return null;
    }

    private CardType transmuteUnique(Wizard wizard, Stash stash, CardType cardType, int index) {
        ++wizard.transmutedUniques;
        if (wizard.transmutedUniques >= requiredUniqueAmount) {
            wizard.transmutedUniques -= requiredUniqueAmount;

            PotionType drop = randomPlugin.get(cardDusts);
            if (stash == wizard.potionStash) {
                insertDrop(stash, cardType, index, drop);
            } else {
                wizard.potionStash.add(drop);
            }
            return drop;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private CardType transmuteRare(Wizard wizard, Stash stash) {
        ++stash.transmutedRares;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedRares >= requiredAmount) {
            stash.transmutedRares -= requiredAmount;

            Stash nextStash = getNextStash(wizard, stash);
            CardType drop = getRandomDrop(wizard, nextStash, Rarity.Rare);
            if (drop != null) {
                nextStash.add(drop);
                return drop;
            }
        }
        return null;
    }

    private CardType transmuteCommon(Wizard wizard, Stash stash, CardType cardType, int index) {
        ++stash.transmutedCommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedCommons >= requiredAmount) {
            stash.transmutedCommons -= requiredAmount;

            CardType drop = getRandomDrop(wizard, stash, Rarity.Uncommon);
            if (drop != null) {
                return insertDrop(stash, cardType, index, drop);
            }
        }
        return null;
    }

    private CardType transmuteUncommon(Wizard wizard, Stash stash, CardType cardType, int index) {
        ++stash.transmutedUncommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedUncommons >= requiredAmount) {
            stash.transmutedUncommons -= requiredAmount;

            CardType drop = getRandomDrop(wizard, stash, Rarity.Rare);
            if (drop != null) {
                return insertDrop(stash, cardType, index, drop);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private CardType getRandomDrop(Wizard wizard, Stash stash, Rarity rarity) {
        CardType drop = stash.getRandomDrop(rarity, randomPlugin);
        if (drop == null) {
            return null;
        }

        if (drop.instance().getRarity() == Rarity.Legendary && !wizard.ownsFoilCard(drop)) {
            return getRandomDrop(wizard, stash, rarity);
        }

        if (stash.isUniqueAlreadyDropped(drop)) {
            return getRandomDrop(wizard, stash, rarity);
        }

        return drop;
    }

    private Stash getNextStash(Wizard wizard, Stash stash) {
        if (stash instanceof TowerStash) {
            return wizard.itemStash;
        }
        return wizard.potionStash;
    }

    private Stash getStash(Wizard wizard, CardCategory cardCategory) {
        if (cardCategory == CardCategory.Tower) {
            return wizard.towerStash;
        }

        if (cardCategory == CardCategory.Item) {
            return wizard.itemStash;
        }

        if (cardCategory == CardCategory.Potion) {
            return wizard.potionStash;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private CardType insertDrop(Stash stash, CardType cardType, int index, CardType drop) {
        if (stash.get(cardType) == null) {
            stash.add(drop, index);
        } else {
            stash.add(drop, index + 1);
        }
        return drop;
    }
}
