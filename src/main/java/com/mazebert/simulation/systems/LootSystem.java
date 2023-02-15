package com.mazebert.simulation.systems;

import com.mazebert.simulation.*;
import com.mazebert.simulation.commands.TransmuteCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.stash.Stash;
import com.mazebert.simulation.stash.StashLookup;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("rawtypes")
public strictfp class LootSystem {
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final FormatPlugin formatPlugin = Sim.context().formatPlugin;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final int version = Sim.context().version;

    public void loot(Tower tower, Creep creep) {
        if (version >= Sim.vDoLEnd) {
            if (creep.getWave().origin == WaveOrigin.Treasure) {
                loot(tower.getWizard(), tower, creep);
            } else {
                unitGateway.forEachWizard(wizard -> loot(wizard, tower, creep));
            }
        } else {
            loot(creep.getWizard(), tower, creep);
        }
    }

    private void loot(Wizard wizard, Tower tower, Creep creep) {
        lootCards(wizard, tower, creep);
        lootGold(wizard, tower, creep);
    }

    private void lootCards(Wizard wizard, Tower tower, Creep creep) {
        if (Sim.context().gameSystem.isTutorial()) {
            return;
        }

        int minDrops = creep.getMinDrops();
        int maxDrops = creep.getMaxDrops();
        int maxItemLevel = creep.getMaxItemLevel();
        float dropChance = Balancing.DEFAULT_DROP_CHANCE * creep.getDropChance() * getTowerItemChanceFactor(tower.getItemChance());
        float[] rarityChances = calculateDropChancesForRarity(tower, creep);

        float diceThrow;

        for (int i = 0; i < maxDrops; ++i) {
            if (i >= minDrops) {
                diceThrow = randomPlugin.getFloatAbs();
                if (diceThrow > dropChance) {
                    continue;
                }
            }

            diceThrow = randomPlugin.getFloatAbs();
            Rarity rarity = calculateDropRarity(rarityChances, diceThrow);

            diceThrow = randomPlugin.getFloatAbs();
            Stash stash = calculateDropStash(wizard, diceThrow);

            rollCardDrop(wizard, creep, maxItemLevel, rarity, stash);
        }
    }

    private void lootGold(Wizard wizard, Tower tower, Creep creep) {
        if (creep.getGold() > 0) {
            grantGold(wizard, creep, creep.getGold() * tower.getGoldModifier());
        }
    }

    public void grantGold(Wizard wizard, Unit source, float gold) {
        int goldRounded = StrictMath.round(gold * source.getGoldModifier() * wizard.getGoldModifier());
        addGold(wizard, source, goldRounded);
    }

    public void grantGoldInterest(Wizard wizard) {
        int goldInterest = StrictMath.round(wizard.gold * (Balancing.GOLD_INTEREST + wizard.interestBonus));
        if (goldInterest <= 0) {
            return;
        }
        if (goldInterest > Balancing.MAX_GOLD_INTEREST) {
            goldInterest = Balancing.MAX_GOLD_INTEREST;
        }
        wizard.addGold(goldInterest);
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(wizard, "Interest: " + formatPlugin.gold(goldInterest, Context.currency));
        }
    }

    public void addGold(Wizard wizard, Unit source, int gold) {
        if (simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(wizard, source, formatPlugin.goldGain(gold), 0xffff00);
        }
        wizard.addGold(gold);
    }

    public void researchTower(Wizard wizard, int round) {
        float[] rarityChances = calculateDropChancesForTowerRarity(round);
        Rarity rarity = calculateDropRarity(rarityChances, randomPlugin.getFloatAbs());

        rollCardDrop(wizard, null, round, rarity, wizard.towerStash);
    }

    public void researchStartingTower(Wizard wizard) {
        rollCardDrop(wizard, null, 1, Rarity.Common, wizard.towerStash);
    }

    @SuppressWarnings("unchecked")
    public boolean dropCard(Wizard wizard, Creep creep, Stash stash, CardType drop, int maxItemLevel) {
        if (drop.instance().getItemLevel() > maxItemLevel) {
            return false;
        }

        if (drop.instance().getRarity() == Rarity.Legendary && !wizard.ownsFoilCard(drop)) {
            return false;
        }

        if (stash.isUniqueAlreadyDropped(drop)) {
            return false;
        }

        addToStash(wizard, creep, stash, drop);

        if (version >= Sim.v13) {
            if (drop.instance().isSupporterReward()) {
                if (version >= Sim.vDoL) {
                    rollCardDrop(wizard, creep, maxItemLevel, Rarity.Legendary, stash, StashLookup.DropableExcludingSupporterCards);
                } else {
                    rollCardDrop(wizard, creep, maxItemLevel, Rarity.Unique, stash);
                }
            }
            if (drop == ItemType.WeddingRing1) {
                addToStash(wizard, creep, stash, ItemType.WeddingRing2);
            } else if (drop == ItemType.WeddingRing2) {
                addToStash(wizard, creep, stash, ItemType.WeddingRing1);
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    public void addToStash(Wizard wizard, Creep creep, Stash stash, CardType drop) {
        stash.add(drop, true);

        if (stash.isAutoTransmute(drop, true)) {
            if (simulationListeners.areNotificationsEnabled() && drop.category() == CardCategory.Tower) {
                simulationListeners.showNotification(wizard, "Auto transmute: " + formatPlugin.card(drop));
            }

            TransmuteCardsCommand command = new TransmuteCardsCommand();
            command.cardCategory = drop.category();
            command.cardType = drop;
            command.all = false;
            command.playerId = wizard.playerId;
            command.automatic = true;
            command.internal = true;

            Sim.context().commandExecutor.execute(command);
        } else {
            simulationListeners.onCardDropped.dispatch(wizard, creep, drop.instance());
        }
    }

    public ItemType addRandomDrop(Wizard wizard, EnumSet<ItemType> itemSet, int itemLevel) {
        List<ItemType> possibleItems = calculatePossibleItems(wizard, itemSet, itemLevel);

        if (!possibleItems.isEmpty()) {
            ItemType itemDrop = randomPlugin.get(possibleItems);

            if (version >= Sim.v14) {
                addToStash(wizard, null, wizard.itemStash, itemDrop);
            } else {
                wizard.itemStash.add(itemDrop, true);
            }

            return itemDrop;
        }

        return null;
    }

    private List<ItemType> calculatePossibleItems(Wizard wizard, EnumSet<ItemType> itemSet, int itemLevel) {
        ItemStash itemStash = wizard.itemStash;
        List<ItemType> possibleItems = new ArrayList<>(itemSet.size());
        for (ItemType item : itemSet) {
            if (item.instance().getRarity() == Rarity.Legendary && !wizard.foilItems.contains(item)) {
                continue;
            }
            if (item.instance().getItemLevel() <= itemLevel && !itemStash.isUniqueAlreadyDropped(item)) {
                possibleItems.add(item);
            }
        }

        return possibleItems;
    }

    private StashLookup getStashLookup(Creep creep) {
        if (creep != null && creep.isEldritch()) {
            return StashLookup.DropableIncludingEldritchCards;
        }
        return StashLookup.Dropable;
    }

    private void rollCardDrop(Wizard wizard, Creep creep, int maxItemLevel, Rarity rarity, Stash stash) {
        rollCardDrop(wizard, creep, maxItemLevel, rarity, stash, getStashLookup(creep));
    }

    private void rollCardDrop(Wizard wizard, Creep creep, int maxItemLevel, Rarity rarity, Stash stash, StashLookup lookup) {
        while (true) {
            CardType drop;
            if (version > Sim.v10) {
                drop = stash.getRandomDrop(rarity, randomPlugin, maxItemLevel, lookup);
            } else {
                drop = stash.getRandomDrop(rarity, randomPlugin);
            }
            if (drop != null && dropCard(wizard, creep, stash, drop, maxItemLevel)) {
                return;
            }
            if (rarity == Rarity.Common) {
                return;
            }
            rarity = Rarity.VALUES[rarity.ordinal() - 1];
        }
    }

    private float[] calculateDropChancesForRarity(Tower tower, Creep creep) {
        float dropQualityProgress = StrictMath.min(1.0f,
                0.8f * StrictMath.min(1.0f, (float) StrictMath.sqrt(creep.getWave().round / 120.0f)) + // Current round affects item quality as well, max quality at lvl ??.
                        0.6f * getTowerItemQualityFactor(tower.getItemQuality()));
        return calculateDropChances(dropQualityProgress, false);
    }

    private float[] calculateDropChancesForTowerRarity(int round) {
        float dropQualityProgress = StrictMath.min(1.0f, (float) StrictMath.sqrt(0.01f * round)); // Current round affects tower rarity as well.
        return calculateDropChances(dropQualityProgress, true);
    }

    private float[] calculateDropChances(float dropQualityProgress, boolean towers) {
        float[] result = Sim.context().tempChancesForRarity;

        result[Rarity.Unique.ordinal()] = 0.12f * dropQualityProgress * dropQualityProgress * dropQualityProgress;
        result[Rarity.Legendary.ordinal()] = 0.4f * result[Rarity.Unique.ordinal()];
        result[Rarity.Rare.ordinal()] = result[Rarity.Unique.ordinal()] + 0.25f * dropQualityProgress * dropQualityProgress;
        if (towers) {
            result[Rarity.Uncommon.ordinal()] = result[Rarity.Rare.ordinal()] + (0.15f + 0.1f * dropQualityProgress);
        } else {
            result[Rarity.Uncommon.ordinal()] = result[Rarity.Rare.ordinal()] + 0.25f * dropQualityProgress;
        }
        result[Rarity.Common.ordinal()] = 1.0f;

        return result;
    }

    float getTowerItemChanceFactor(float linearChance) {
        if (linearChance > 1.0f) {
            return 1.0f + Balancing.DROP_CHANCE_CONST * ((-2.0f * linearChance) / (1.0f + linearChance * linearChance) + 1);
        } else if (linearChance > 0.0f) {
            return (2.0f * linearChance) / (1.0f + linearChance);
        } else {
            return 0.0f;
        }
    }

    private float getTowerItemQualityFactor(float linearChance) {
        if (linearChance > 0.0f) {
            return (Balancing.DROP_QUALITY_CONST * (linearChance - 1.0f)) / (1.0f + (Balancing.DROP_QUALITY_CONST * (linearChance - 1.0f)));
        } else {
            return 0.0f;
        }
    }

    private Rarity calculateDropRarity(float[] rarityChances, float diceThrow) {
        // Find rarity that fits the dice throw.
        for (int i = Rarity.VALUES.length - 1; i >= 0; --i) {
            if (diceThrow <= rarityChances[i]) {
                return Rarity.VALUES[i];
            }
        }

        return Rarity.Common;
    }

    private Stash calculateDropStash(Wizard wizard, float diceThrow) {
        if (diceThrow < 0.6f) {
            return wizard.itemStash;
        } else {
            return wizard.potionStash;
        }
    }
}
