package com.mazebert.simulation.systems;

import com.mazebert.simulation.*;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.stash.Stash;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class LootSystem {
    private final RandomPlugin randomPlugin;
    private final SimulationListeners simulationListeners;

    public LootSystem(RandomPlugin randomPlugin, SimulationListeners simulationListeners) {
        this.randomPlugin = randomPlugin;
        this.simulationListeners = simulationListeners;
    }

    public void loot(Tower tower, Creep creep) {
        Wizard wizard = tower.getWizard();

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

        if (creep.getGold() > 0) {
            int gold = StrictMath.round(creep.getGold() * creep.getGoldModifier() * tower.getGoldModifier());
            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(creep, Sim.context().formatPlugin.goldGain(gold), 0xffff00);
            }
            wizard.addGold(gold);
        }
    }

    public void researchTower(Wizard wizard, int round) {
        float[] rarityChances = calculateDropChancesForTowerRarity(round);
        Rarity rarity = calculateDropRarity(rarityChances, randomPlugin.getFloatAbs());

        rollCardDrop(wizard, null, round, rarity, wizard.towerStash);
    }

    @SuppressWarnings("unchecked")
    public void dropCard(Wizard wizard, Creep creep, Stash stash, CardType drop) {
        stash.add(drop);
        simulationListeners.onCardDropped.dispatch(wizard, creep, drop.instance());
    }

    private void rollCardDrop(Wizard wizard, Creep creep, int maxItemLevel, Rarity rarity, Stash stash) {
        while (rarity.ordinal() >= Rarity.Common.ordinal()) {
            CardType drop = stash.getRandomDrop(rarity, maxItemLevel, randomPlugin);
            if (drop == null) {
                if (rarity == Rarity.Common) {
                    break;
                } else {
                    rarity = Rarity.values()[rarity.ordinal() - 1];
                }
            } else {
                dropCard(wizard, creep, stash, drop);
                break;
            }
        }
    }

    private float[] calculateDropChancesForRarity(Tower tower, Creep creep) {
        float dropQualityProgress = StrictMath.min(1.0f,
                0.8f * StrictMath.min(1.0f, (float) StrictMath.sqrt(creep.getWave().round / 120.0f)) + // Current round affects item quality as well, max quality at lvl ??.
                        0.6f * getTowerItemQualityFactor(tower.getItemQuality()));

        float[] result = Sim.context().tempChancesForRarity;

        result[Rarity.Unique.ordinal()] = 0.25f * dropQualityProgress * dropQualityProgress * dropQualityProgress;
        result[Rarity.Legendary.ordinal()] = 0.5f * result[Rarity.Unique.ordinal()];
        result[Rarity.Rare.ordinal()] = result[Rarity.Unique.ordinal()] + 0.25f * dropQualityProgress * dropQualityProgress;
        result[Rarity.Uncommon.ordinal()] = result[Rarity.Rare.ordinal()] + 0.25f * dropQualityProgress;
        result[Rarity.Common.ordinal()] = 1.0f;

        return result;
    }

    private float[] calculateDropChancesForTowerRarity(int round) {
        float dropQualityProgress = StrictMath.min(1.0f, (float)StrictMath.sqrt(0.01f * round)); // Current round affects tower rarity as well.

        float[] result = Sim.context().tempChancesForRarity;
        result[Rarity.Unique.ordinal()] = 0.25f * dropQualityProgress * dropQualityProgress * dropQualityProgress;
        result[Rarity.Legendary.ordinal()] = 0.5f * result[Rarity.Unique.ordinal()];
        result[Rarity.Rare.ordinal()] = result[Rarity.Unique.ordinal()] + 0.25f * dropQualityProgress * dropQualityProgress;
        result[Rarity.Uncommon.ordinal()] = result[Rarity.Rare.ordinal()] + (0.15f + 0.1f * dropQualityProgress);
        result[Rarity.Common.ordinal()] = 1.0f;

        return result;
    }

    private float getTowerItemChanceFactor(float linearChance) {
        if (linearChance > 0.0f) {
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
        for (int i = Rarity.values().length - 1; i >= 0; --i) {
            if (diceThrow <= rarityChances[i]) {
                return Rarity.values()[i];
            }
        }

        return Rarity.Common;
    }

    private Stash calculateDropStash(Wizard wizard, float diceThrow) {
        if (diceThrow < 0.66f) {
            return wizard.itemStash;
        } else {
            return wizard.potionStash;
        }
    }
}
