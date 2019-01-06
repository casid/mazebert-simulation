package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.OnCardsTransmuted;
import com.mazebert.simulation.listeners.OnGoldChanged;
import com.mazebert.simulation.listeners.OnHealthChanged;
import com.mazebert.simulation.listeners.OnLevelChanged;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.stash.PotionStash;
import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumSet;

public strictfp class Wizard extends Unit {
    public final OnHealthChanged onHealthChanged = new OnHealthChanged();
    public final OnLevelChanged onLevelChanged = new OnLevelChanged();
    public final OnGoldChanged onGoldChanged = new OnGoldChanged();
    public final OnCardsTransmuted onCardsTransmuted = new OnCardsTransmuted();

    public final TowerStash towerStash = new TowerStash();
    public final ItemStash itemStash = new ItemStash();
    public final PotionStash potionStash = new PotionStash();

    public int playerId;
    public String name = "Unknown Wizard"; // TODO
    public float health = 1.0f;
    public long experience;
    public double experienceModifier = 1;
    public int level = 1;
    public long gold;
    public float interestBonus;
    public int requiredTransmuteAmount = 4;
    public int transmutedUniques;

    public transient double bestHit;
    public transient TowerType bestHitTower;
    public transient double mostDamage;
    public transient TowerType mostDamageTower;
    public transient long mostKills;
    public transient TowerType mostKillsTower;
    public transient double totalDamage;
    public transient long kills;
    public transient long goldFarmed;

    public transient EnumSet<HeroType> foilHeroes;
    public transient EnumSet<TowerType> foilTowers;
    public transient EnumSet<PotionType> foilPotions;
    public transient EnumSet<ItemType> foilItems;

    public Wizard() {
        setWizard(this);
    }

    @Override
    public void hash(Hash hash) {
        super.hash(hash);

        hash.add(towerStash);
        hash.add(itemStash);
        hash.add(potionStash);

        hash.add(playerId);
        // ignore name
        hash.add(health);
        hash.add(experience);
        hash.add(experienceModifier);
        hash.add(level);
        hash.add(gold);
        hash.add(interestBonus);
        hash.add(requiredTransmuteAmount);
        hash.add(transmutedUniques);

        // ignore transient attributes
    }

    @Override
    public String getModelId() {
        return null;
    }

    public void addGold(long amount) {
        gold += amount;
        if (amount > 0) {
            goldFarmed += amount;
        }
        onGoldChanged.dispatch(this, gold - amount, gold);
    }

    public void addHealth(float amount) {
        health += amount;
        onHealthChanged.dispatch(this, health - amount, health);
    }
}
