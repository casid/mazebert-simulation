package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.OnCardsCrafted;
import com.mazebert.simulation.listeners.OnGoldChanged;
import com.mazebert.simulation.listeners.OnHealthChanged;
import com.mazebert.simulation.listeners.OnLevelChanged;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.stash.PotionStash;
import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.Currency;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class Wizard extends Unit {
    public final OnHealthChanged onHealthChanged = new OnHealthChanged();
    public final OnLevelChanged onLevelChanged = new OnLevelChanged();
    public final OnGoldChanged onGoldChanged = new OnGoldChanged();
    public final OnCardsCrafted onCardsCrafted = new OnCardsCrafted();

    public final TowerStash towerStash = new TowerStash();
    public final ItemStash itemStash = new ItemStash();
    public final PotionStash potionStash = new PotionStash();

    public int playerId;
    public float health = 1.0f;
    public long experience;
    public double experienceModifier = 1;
    public int level = 1;
    public long gold;
    public float interestBonus;
    public int requiredCraftAmount = 4;
    public int craftedUniques;

    public double bestHit;
    public double totalDamage;
    public TowerType bestHitTower;


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
        hash.add(health);
        hash.add(experience);
        hash.add(experienceModifier);
        hash.add(level);
        hash.add(gold);
        hash.add(interestBonus);
        hash.add(requiredCraftAmount);
        hash.add(craftedUniques);

        hash.add(bestHit);
        hash.add(totalDamage);
    }

    @Override
    public String getModelId() {
        return null;
    }

    public void addGold(long amount) {
        gold += amount;
        onGoldChanged.dispatch(this, gold - amount, gold);
    }

    public void addHealth(float amount) {
        health += amount;
        onHealthChanged.dispatch(this, health - amount, health);
    }
}
