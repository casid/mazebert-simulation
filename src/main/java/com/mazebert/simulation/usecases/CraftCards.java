package com.mazebert.simulation.usecases;

import com.mazebert.simulation.*;
import com.mazebert.simulation.commands.CraftCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.stash.PotionStash;
import com.mazebert.simulation.stash.Stash;
import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.List;

public strictfp class CraftCards extends Usecase<CraftCardsCommand> {

    private static final PotionType[] cardDusts = {
            PotionType.CardDustCrit,
            PotionType.CardDustLuck,
            PotionType.CardDustLevel,
            PotionType.CardDustVital
    };

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

    @Override
    public void execute(CraftCardsCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        Stash stash = getStash(wizard, command.cardCategory);
        if (stash == null) {
            return;
        }

        if (command.all) {
            craftAll(wizard, stash, command.cardType);
        } else {
            craft(wizard, stash, command.cardType);
        }
    }

    @SuppressWarnings("unchecked")
    public void craftAll(Wizard wizard, Stash stash, CardType cardType) {
        List<CardType> result = null;

        Card card;
        while ((card = stash.remove(cardType)) != null) {
            CardType drop = craft(wizard, stash, card);
            if (drop != null) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(drop);
            }
        }

        if (result != null) {
            wizard.onCardsCrafted.dispatch(result);
        } else {
            wizard.onCardsCrafted.dispatch();
        }
    }

    @SuppressWarnings("unchecked")
    public void craft(Wizard wizard, Stash stash, CardType cardType) {
        Card card = stash.remove(cardType);
        if (card == null) {
            return;
        }

        CardType drop = craft(wizard, stash, card);
        if (drop != null) {
            wizard.onCardsCrafted.dispatch(drop);
        } else {
            wizard.onCardsCrafted.dispatch();
        }
    }

    private CardType craft(Wizard wizard, Stash stash, Card card) {
        switch (card.getRarity()) {
            case Common:
                return craftCommon(wizard, stash);
            case Uncommon:
                return craftUncommon(wizard, stash);
            case Rare:
                return craftRare(wizard, stash);
            case Unique:
            case Legendary:
                return craftUnique(wizard);
        }

        return null;
    }

    private CardType craftUnique(Wizard wizard) {
        ++wizard.craftedUniques;
        if (wizard.craftedUniques >= 2) {
            wizard.craftedUniques -= 2;

            PotionType drop = randomPlugin.get(cardDusts);
            wizard.potionStash.add(drop);
            return drop;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private CardType craftRare(Wizard wizard, Stash stash) {
        ++stash.craftedRares;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.craftedRares >= requiredAmount) {
            stash.craftedRares -= requiredAmount;

            Stash nextStash = getNextStash(wizard, stash);
            CardType drop = nextStash.getRandomDrop(Rarity.Rare, Integer.MAX_VALUE, randomPlugin);
            if (drop != null) {
                nextStash.add(drop);
                return drop;
            }
        }
        return null;
    }

    private int getRequiredAmount(Wizard wizard, Stash stash) {
        if (stash instanceof PotionStash) {
            return 2;
        }
        return wizard.requiredCraftAmount;
    }

    @SuppressWarnings("unchecked")
    private CardType craftCommon(Wizard wizard, Stash stash) {
        ++stash.craftedCommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.craftedCommons >= requiredAmount) {
            stash.craftedCommons -= requiredAmount;

            CardType drop = stash.getRandomDrop(Rarity.Uncommon, Integer.MAX_VALUE, randomPlugin);
            if (drop != null) {
                stash.add(drop);
                return drop;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private CardType craftUncommon(Wizard wizard, Stash stash) {
        ++stash.craftedUncommons;
        int requiredAmount = getRequiredAmount(wizard, stash);
        if (stash.craftedUncommons >= requiredAmount) {
            stash.craftedUncommons -= requiredAmount;

            CardType drop = stash.getRandomDrop(Rarity.Rare, Integer.MAX_VALUE, randomPlugin);
            if (drop != null) {
                stash.add(drop);
                return drop;
            }
        }
        return null;
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
}
