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

        Stash stash = wizard.getStash(command.cardCategory);
        if (stash == null) {
            return;
        }

        if (!command.cardType.instance().isTradingAllowed()) {
            return;
        }

        if (command.all) {
            transmuteAll(wizard, stash, command.cardType, command.notify);
        } else {
            if (command.cardType == ItemType.TransmuteStack) {
                return;
            }
            transmute(wizard, stash, command.cardType, command.notify);
        }
    }

    @SuppressWarnings("unchecked")
    public void transmuteAll(Wizard wizard, Stash stash, CardType cardType, boolean notify) {
        List<CardType> result = null;
        int transmutedCards = 0;

        int index = stash.getIndex(cardType);

        Card card;
        while ((card = stash.remove(cardType, false)) != null) {
            CardType drop = transmute(wizard, stash, card, cardType, index, notify);
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
    public void transmute(Wizard wizard, Stash stash, CardType cardType, boolean notify) {
        int index = stash.getIndex(cardType);

        Card card = stash.remove(cardType, false);
        if (card == null) {
            return;
        }

        Rarity rarity = cardType.instance().getRarity();

        CardType drop = transmute(wizard, stash, card, cardType, index, notify);
        wizard.onCardsTransmuted.dispatch(rarity, drop);
    }

    private CardType transmute(Wizard wizard, Stash stash, Card card, CardType cardType, int index, boolean notify) {
        switch (card.getRarity()) {
            case Common:
                return transmuteCommon(wizard, stash, cardType, index, notify);
            case Uncommon:
                return transmuteUncommon(wizard, stash, cardType, index, notify);
            case Rare:
                return transmuteRare(wizard, stash, notify);
            case Unique:
            case Legendary:
                return transmuteUnique(wizard, stash, cardType, index, notify);
        }

        return null;
    }

    private CardType transmuteUnique(Wizard wizard, Stash stash, CardType cardType, int index, boolean notify) {
        ++wizard.transmutedUniques;
        if (wizard.transmutedUniques >= requiredUniqueAmount) {
            wizard.transmutedUniques -= requiredUniqueAmount;

            PotionType drop = randomPlugin.get(cardDusts);
            if (stash == wizard.potionStash) {
                insertDrop(wizard, stash, cardType, index, drop, notify);
            } else {
                wizard.potionStash.add(drop);
            }
            return drop;
        }
        return null;
    }

    private CardType transmuteRare(Wizard wizard, Stash stash, boolean notify) {
        ++stash.transmutedRares;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedRares >= requiredAmount) {
            stash.transmutedRares -= requiredAmount;

            Stash nextStash = getNextStash(wizard, stash);
            CardType drop = getRandomDrop(wizard, nextStash, Rarity.Rare);
            if (drop != null) {
                return insertDrop(wizard, nextStash, null, -1, drop, notify);
            }
        }
        return null;
    }

    private CardType transmuteCommon(Wizard wizard, Stash stash, CardType cardType, int index, boolean notify) {
        ++stash.transmutedCommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedCommons >= requiredAmount) {
            stash.transmutedCommons -= requiredAmount;

            CardType drop = getRandomDrop(wizard, stash, Rarity.Uncommon);
            if (drop != null) {
                return insertDrop(wizard, stash, cardType, index, drop, notify);
            }
        }
        return null;
    }

    private CardType transmuteUncommon(Wizard wizard, Stash stash, CardType cardType, int index, boolean notify) {
        ++stash.transmutedUncommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.transmutedUncommons >= requiredAmount) {
            stash.transmutedUncommons -= requiredAmount;

            CardType drop = getRandomDrop(wizard, stash, Rarity.Rare);
            if (drop != null) {
                return insertDrop(wizard, stash, cardType, index, drop, notify);
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
    private CardType insertDrop(Wizard wizard, Stash stash, CardType cardType, int index, CardType drop, boolean notify) {
        if (stash.isAutoTransmute(drop)) {
            return transmute(wizard, stash, drop.instance(), drop, index, notify);
        }

        if (index == -1) {
            stash.add(drop, notify);
        } else if (stash.get(cardType) == null) {
            stash.add(drop, index, notify);
        } else {
            stash.add(drop, index + 1, notify);
        }
        return drop;
    }
}
