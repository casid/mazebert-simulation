package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.stash.PotionStash;
import com.mazebert.simulation.stash.Stash;
import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.quests.Quest;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumSet;

public strictfp class Wizard extends Unit {
    public final OnWizardHealthChanged onHealthChanged = new OnWizardHealthChanged();
    public final OnLevelChanged onLevelChanged = new OnLevelChanged();
    public final OnGoldChanged onGoldChanged = new OnGoldChanged();
    public final OnCardsTransmuted onCardsTransmuted = new OnCardsTransmuted();
    public final OnQuestCompleted onQuestCompleted = new OnQuestCompleted();
    public final OnElementResearchPointsChanged onElementResearchPointsChanged = new OnElementResearchPointsChanged();

    public final TowerStash towerStash = new TowerStash();
    public final ItemStash itemStash = new ItemStash();
    public final PotionStash potionStash = new PotionStash();

    public int playerId;
    public long ladderPlayerId;
    public String name = "Unknown Wizard";
    public float health = 1.0f;
    public long initialExperience;
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

    public transient EnumSet<HeroType> foilHeroes = EnumSet.noneOf(HeroType.class);
    public transient EnumSet<TowerType> foilTowers = EnumSet.noneOf(TowerType.class);
    public transient EnumSet<PotionType> foilPotions = EnumSet.noneOf(PotionType.class);
    public transient EnumSet<ItemType> foilItems = EnumSet.noneOf(ItemType.class);

    public transient int elementResearchPoints;
    public transient int maxElementResearchPoints;

    public Wizard() {
        setWizard(this);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void hash(Hash hash) {
        super.hash(hash);

        hash.add(towerStash);
        hash.add(itemStash);
        hash.add(potionStash);

        hash.add(playerId);
        hash.add(ladderPlayerId);
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

    public boolean ownsFoilCard(CardType type) {
        Class<? extends CardType> typeClass = type.getClass();
        if (typeClass == ItemType.class) {
            return foilItems.contains(type);
        }
        if (typeClass == TowerType.class) {
            return foilTowers.contains(type);
        }
        if (typeClass == PotionType.class) {
            return foilPotions.contains(type);
        }
        if (typeClass == HeroType.class) {
            return foilHeroes.contains(type);
        }
        return false;
    }

    @Override
    public void addAbility(Ability ability) {
        if (ability instanceof WizardPower) {
            if (level < ((WizardPower) ability).getRequiredLevel()) {
                return;
            }
        }
        super.addAbility(ability);
    }

    public void addQuestProgress(Class<? extends Quest> questClass) {
        Quest quest = getAbility(questClass);
        if (quest != null) {
            quest.addAmount(1);
        }
    }

    public void addElementResearchPoint() {
        if (maxElementResearchPoints > 0) {
            --maxElementResearchPoints;
            ++elementResearchPoints;
            onElementResearchPointsChanged.dispatch(this);
        }
    }

    public EnumSet<PotionType> getResearchableElements() {
        EnumSet<PotionType> result = EnumSet.noneOf(PotionType.class);
        for (Element element : Element.getValues()) {
            if (!towerStash.isElementResearched(element)) {
                PotionType cardForElement = getCardForElement(element);
                if (cardForElement != null && !potionStash.isUniqueAlreadyDropped(cardForElement)) {
                    result.add(cardForElement);
                }
            }
        }
        return result;
    }

    public Stash getStash(CardCategory cardCategory) {
        if (cardCategory == CardCategory.Tower) {
            return towerStash;
        }

        if (cardCategory == CardCategory.Item) {
            return itemStash;
        }

        if (cardCategory == CardCategory.Potion) {
            return potionStash;
        }

        return null;
    }

    private PotionType getCardForElement(Element element) {
        switch (element) {
            case Nature:
                return PotionType.ResearchNature;
            case Metropolis:
                return PotionType.ResearchMetropolis;
            case Darkness:
                return PotionType.ResearchDarkness;
            case Light:
                return PotionType.ResearchLight;
        }

        return null;
    }
}
