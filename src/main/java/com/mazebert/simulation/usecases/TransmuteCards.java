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
import com.mazebert.simulation.units.items.ToiletPaperTransmuteAbility;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
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

        Stash stash = wizard.getStash(command.cardCategory);
        if (stash == null) {
            return;
        }

        if (!command.cardType.instance().isTradingAllowed()) {
            return;
        }

        if (command.cardType == ItemType.ToiletPaper) {
            transmuteToiletPaper(wizard, stash, command.cardType);
            return;
        }

        if (command.all) {
            transmuteAll(wizard, stash, command.cardType, command.amountToKeep);
        } else {
            if (command.cardType == ItemType.TransmuteStack) {
                return;
            }
            transmute(wizard, stash, command.cardType, command.automatic);
        }
    }

    @SuppressWarnings("unchecked")
    private void transmuteToiletPaper(Wizard wizard, Stash stash, CardType cardType) {
        int version = Sim.context().version;
        int index = stash.getIndex(cardType);
        stash.remove(cardType);

        List<CardType> result = new ArrayList<>();

        List<CardType> possibleItems = new ArrayList<>();
        stash.addPossibleDropsExcludingSupporterCards(wizard, Rarity.Unique, possibleItems);
        stash.addPossibleDropsExcludingSupporterCards(wizard, Rarity.Legendary, possibleItems);

        int amount;
        for (amount = 0; amount < ToiletPaperTransmuteAbility.AMOUNT && !possibleItems.isEmpty(); ++amount) {
            CardType item = randomPlugin.get(possibleItems);

            addToiletPaperRewardCard(wizard, stash, cardType, index, result, possibleItems, item);

            if (version >= Sim.vDoLEnd) {
                if (item == ItemType.WeddingRing1) {
                    addToiletPaperRewardCard(wizard, stash, cardType, index, result, possibleItems, ItemType.WeddingRing2);
                } else if (item == ItemType.WeddingRing2) {
                    addToiletPaperRewardCard(wizard, stash, cardType, index, result, possibleItems, ItemType.WeddingRing1);
                }
            }
        }

        if (version >= Sim.vDoLEnd) {
            for (; amount < ToiletPaperTransmuteAbility.AMOUNT; ++amount) {
                PotionType dust = randomPlugin.get(cardDusts);
                wizard.potionStash.add(dust);
                result.add(dust);
            }
        }

        wizard.onCardsTransmuted.dispatch(Rarity.Legendary, result, 1);
    }

    private void addToiletPaperRewardCard(Wizard wizard, Stash stash, CardType cardType, int index, List<CardType> result, List<CardType> possibleItems, CardType item) {
        possibleItems.remove(item);
        insertDrop(wizard, stash, cardType, index, item, false);
        result.add(item);
    }

    @SuppressWarnings("unchecked")
    private void transmuteAll(Wizard wizard, Stash stash, CardType cardType, int amountToKeep) {
        List<CardType> result = null;
        int transmutedCards = 0;

        int index = stash.getIndex(cardType);

        while (stash.getAmount(cardType) > amountToKeep) {
            Card card = stash.remove(cardType, false, false);
            CardType drop = transmute(wizard, stash, card, cardType, index, false);
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
    private void transmute(Wizard wizard, Stash stash, CardType cardType, boolean automatic) {
        int index = stash.getIndex(cardType);

        Card card = stash.remove(cardType, false, automatic);
        if (card == null) {
            return;
        }

        Rarity rarity = cardType.instance().getRarity();

        CardType drop = transmute(wizard, stash, card, cardType, index, automatic);
        wizard.onCardsTransmuted.dispatch(rarity, drop, automatic);
    }

    private CardType transmute(Wizard wizard, Stash stash, Card card, CardType cardType, int index, boolean automatic) {
        switch (card.getRarity()) {
            case Common:
                return transmuteCommon(wizard, stash, cardType, index, automatic);
            case Uncommon:
                return transmuteUncommon(wizard, stash, cardType, index, automatic);
            case Rare:
                return transmuteRare(wizard, stash, automatic);
            case Unique:
            case Legendary:
                return transmuteUnique(wizard, stash, cardType, index, automatic);
        }

        return null;
    }

    private CardType transmuteUnique(Wizard wizard, Stash stash, CardType cardType, int index, boolean automatic) {
        ++wizard.transmutedUniques;
        if (wizard.transmutedUniques >= requiredUniqueAmount) {
            wizard.transmutedUniques -= requiredUniqueAmount;

            PotionType drop = randomPlugin.get(cardDusts);
            if (stash == wizard.potionStash) {
                insertDrop(wizard, stash, cardType, index, drop, automatic);
            } else {
                wizard.potionStash.add(drop);
            }
            return drop;
        }
        return null;
    }

    private CardType transmuteRare(Wizard wizard, Stash stash, boolean automatic) {
        ++stash.transmutedRares;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedRares >= requiredAmount) {
            stash.transmutedRares -= requiredAmount;

            Stash nextStash = getNextStash(wizard, stash);
            CardType drop = getRandomDrop(wizard, nextStash, Rarity.Rare);
            if (drop != null) {
                return insertDrop(wizard, nextStash, null, -1, drop, automatic);
            }
        }
        return null;
    }

    private CardType transmuteCommon(Wizard wizard, Stash stash, CardType cardType, int index, boolean automatic) {
        ++stash.transmutedCommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedCommons >= requiredAmount) {
            stash.transmutedCommons -= requiredAmount;

            CardType drop = getRandomDrop(wizard, stash, Rarity.Uncommon);
            if (drop != null) {
                return insertDrop(wizard, stash, cardType, index, drop, automatic);
            }
        }
        return null;
    }

    private CardType transmuteUncommon(Wizard wizard, Stash stash, CardType cardType, int index, boolean automatic) {
        ++stash.transmutedUncommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedUncommons >= requiredAmount) {
            stash.transmutedUncommons -= requiredAmount;

            CardType drop = getRandomDrop(wizard, stash, Rarity.Rare);
            if (drop != null) {
                return insertDrop(wizard, stash, cardType, index, drop, automatic);
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

    @SuppressWarnings("unchecked")
    private CardType insertDrop(Wizard wizard, Stash stash, CardType cardType, int index, CardType drop, boolean automatic) {
        if (stash.isAutoTransmute(drop)) {
            return transmute(wizard, stash, drop.instance(), drop, index, automatic);
        }

        if (index == -1) {
            stash.add(drop, automatic);
        } else if (stash.get(cardType) == null) {
            stash.add(drop, index, automatic);
        } else {
            stash.add(drop, index + 1, automatic);
        }
        return drop;
    }
}
