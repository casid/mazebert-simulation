package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.CooldownUnit;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.Item;

public strictfp abstract class Tower extends Unit implements CooldownUnit, Card, OnKillListener {

    public final OnAttack onAttack = new OnAttack();
    public final OnDamage onDamage = new OnDamage();
    public final OnKill onKill = new OnKill();
    public final OnMiss onMiss = new OnMiss();
    public final OnLevelChanged onLevelChanged = new OnLevelChanged();
    public final OnItemEquipped onItemEquipped = new OnItemEquipped();
    public final OnPotionConsumed onPotionConsumed = new OnPotionConsumed();

    private int level;
    private float experience;
    private float strength = 1.0f;
    private float baseCooldown = Float.MAX_VALUE;
    private float attackSpeedAdd;
    private float baseRange = 1.0f;
    private float damageSpread; // [0, 1], 0=constant damage, 1=max spread damage
    private float minBaseDamage;
    private float maxBaseDamage;
    private float addedRelativeBaseDamage;
    private float addedAbsoluteBaseDamage;
    private float critChance = 0.05f;
    private float critDamage = 0.25f;
    private int multicrit = 1;
    private float chanceToMiss;
    private float luck = 1.0f; // factor 1 is regular luck of every tower
    private float itemChance = 1.0f; // 1.0 is 100% item chance (normal, not good not bad)
    private float itemQuality = 1.0f; // 1.0 is 100% item quality (normal, not good not bad)
    private Element element;
    private Gender gender;
    private AttackType attackType;
    private float experienceModifier = 1.0f; // factor 1 is regular experience gain

    private Item[] items = new Item[4];

    private int kills;

    public Tower() {
        onKill.add(this);
    }

    @Override
    public void hash(Hash hash) {
        super.hash(hash);

        hash.add(level);
        hash.add(experience);
        hash.add(strength);
        hash.add(baseCooldown);
        hash.add(attackSpeedAdd);
        hash.add(baseRange);
        hash.add(damageSpread);
        hash.add(minBaseDamage);
        hash.add(maxBaseDamage);
        hash.add(addedRelativeBaseDamage);
        hash.add(addedAbsoluteBaseDamage);
        hash.add(critChance);
        hash.add(critDamage);
        hash.add(multicrit);
        hash.add(chanceToMiss);
        hash.add(luck);
        hash.add(itemChance);
        hash.add(itemQuality);
        hash.add(element);
        hash.add(gender);
        hash.add(attackType);
        hash.add(experienceModifier);

        hash.add(kills);
    }

    public float getBaseCooldown() {
        return baseCooldown;
    }

    public void setBaseCooldown(float baseCooldown) {
        this.baseCooldown = baseCooldown;
    }

    public float getBaseRange() {
        return baseRange;
    }

    public void setBaseRange(float baseRange) {
        this.baseRange = baseRange;
    }

    public float getMinBaseDamage() {
        return minBaseDamage;
    }

    public float getMaxBaseDamage() {
        return maxBaseDamage;
    }

    public void setBaseDamage(float baseDamage) {
        maxBaseDamage = StrictMath.round((1.0 + damageSpread) * baseDamage);
        minBaseDamage = StrictMath.round((1.0 - damageSpread) * baseDamage);

        if (minBaseDamage <= 0.0f) {
            minBaseDamage = 1.0f;
        }
    }

    public void setDamageSpread(float damageSpread) {
        this.damageSpread = damageSpread;
    }

    public float getCritChance() {
        return critChance;
    }

    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }

    public float getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(float critDamage) {
        this.critDamage = critDamage;
    }

    public int getMulticrit() {
        if (multicrit < 1) {
            return 1;
        }
        return multicrit;
    }

    public void setMulticrit(int multicrit) {
        this.multicrit = multicrit;
    }

    public void addMulticrit(int amount) {
        multicrit += amount;
    }

    public float getAddedRelativeBaseDamage() {
        return addedRelativeBaseDamage;
    }

    public void setAddedRelativeBaseDamage(float addedRelativeBaseDamage) {
        this.addedRelativeBaseDamage = addedRelativeBaseDamage;
    }

    public float getAddedAbsoluteBaseDamage() {
        return addedAbsoluteBaseDamage;
    }

    public void setAddedAbsoluteBaseDamage(float addedAbsoluteBaseDamage) {
        this.addedAbsoluteBaseDamage = addedAbsoluteBaseDamage;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level != this.level) {
            int oldLevel = this.level;
            this.level = level;
            setBaseDamage(Balancing.getBaseDamage(this));
            onLevelChanged.dispatch(this, oldLevel, level);
        }
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    @Override
    public float getCooldown() {
        float modifier = getAttackSpeedModifier();
        if (modifier <= 0) {
            return Balancing.MAX_COOLDOWN;
        }

        float cooldown = baseCooldown / modifier;
        if (cooldown > Balancing.MAX_COOLDOWN) {
            return Balancing.MAX_COOLDOWN;
        }
        if (cooldown < Balancing.MIN_COOLDOWN) {
            return Balancing.MIN_COOLDOWN;
        }
        return cooldown;
    }

    public float getAttackSpeedAdd() {
        return attackSpeedAdd;
    }

    public void setAttackSpeedAdd(float attackSpeedAdd) {
        this.attackSpeedAdd = attackSpeedAdd;
    }

    public float getAttackSpeedModifier() {
        float modifier = 1.0f + attackSpeedAdd;
        return modifier < 0 ? 0 : modifier;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public String getAuthor() {
        return "casid";
    }

    @Override
    public boolean isDark() {
        return element == Element.Darkness;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public int getImageOffsetOnCardX() {
        return 0;
    }

    public int getImageOffsetOnCardY() {
        return 0;
    }

    @Override
    public Rarity getDropRarity() {
        return getRarity();
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    public int getGoldCost() {
        switch (getDropRarity()) {
            case Common:
                return (int) (getGoldCostFactor() * 50);
            case Uncommon:
                return (int) (getGoldCostFactor() * 250);
            case Rare:
                return (int) (getGoldCostFactor() * 2000);
            case Unique:
                return (int) (getGoldCostFactor() * 4000);
            case Legendary:
                return (int) (getGoldCostFactor() * 8000);
        }
        return 1;
    }

    public float getLuck() {
        return luck;
    }

    public void setLuck(float luck) {
        this.luck = luck;
    }

    protected abstract float getGoldCostFactor();

    public void addCritChance(float critChance) {
        this.critChance += critChance;
    }

    public void addCritDamage(float critDamage) {
        this.critDamage += critDamage;
    }

    public boolean isAbilityTriggered(float chance) {
        chance *= luck;
        if (chance > Balancing.MAX_TRIGGER_CHANCE) {
            chance = Balancing.MAX_TRIGGER_CHANCE;
        }
        return Sim.context().randomPlugin.getFloatAbs() <= chance;
    }

    public boolean isNegativeAbilityTriggered(float chance) {
        return Sim.context().randomPlugin.getFloatAbs() * luck <= chance;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    @Override
    public void onKill(Creep target) {
        setExperience(experience + experienceModifier * target.getExperienceModifier() * target.getExperience());
        ++kills;
        LootSystem.loot(this, target);
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
        setLevel(Balancing.getTowerLevelForExperience(experience));
    }

    public float getExperienceModifier() {
        return experienceModifier;
    }

    public void setExperienceModifier(float experienceModifier) {
        this.experienceModifier = experienceModifier;
    }

    public Item getItem(int index) {
        return items[index];
    }

    public void setItem(int index, Item item) {
        Item previousItem = items[index];
        if (previousItem != null) {
            for (Ability ability : previousItem.getAbilities()) {
                removeAbility(ability);
            }
        }

        items[index] = item;

        if (item != null) {
            for (Ability ability : item.getAbilities()) {
                addAbility(ability);
            }
        }

        onItemEquipped.dispatch(this, index, previousItem, item);
    }

    public void addAddedRelativeBaseDamage(float amount) {
        addedRelativeBaseDamage += amount;
    }

    public int getInventorySize() {
        return items.length;
    }

    public double rollBaseDamage(RandomPlugin randomPlugin) {
        if (minBaseDamage == maxBaseDamage) {
            return minBaseDamage;
        } else {
            return randomPlugin.getDouble(minBaseDamage, maxBaseDamage);
        }
    }

    @SuppressWarnings("unused") // Used by UI
    public double calculateAverageBaseDamageForDisplay() {
        return minBaseDamage + 0.5 * (maxBaseDamage - minBaseDamage);
    }

    @SuppressWarnings("unused") // Used by UI
    public double calculateAverageDamageForDisplay() {
        // Calculate average base damage between min and max.
        double averageBaseDamage = minBaseDamage + (maxBaseDamage - minBaseDamage + 1) * 0.5;
        averageBaseDamage += addedAbsoluteBaseDamage;
        averageBaseDamage = averageBaseDamage * (1.0 + addedRelativeBaseDamage);
        double damage = averageBaseDamage;

        // Calculate average crit amount and damage.
        double critChance = this.critDamage;
        int multicrit = getMulticrit();
        for (int i = 0; i < multicrit; ++i)
        {
            if (critChance > 0.0)
            {
                // Add average critical strike damage to the final damage.
                damage += averageBaseDamage * critDamage * Math.min(1.0, critChance);

                // Reduce crit chance for next multicrit by 20%.
                critChance *= 0.8;
            }
            else
            {
                break;
            }
        }

        return damage;
    }

    public float getItemChance() {
        return itemChance;
    }

    public void setItemChance(float itemChance) {
        this.itemChance = itemChance;
    }

    public float getItemQuality() {
        return itemQuality;
    }

    public void setItemQuality(float itemQuality) {
        this.itemQuality = itemQuality;
    }

    private Item[] getItemsCopy() {
        Item[] copy = new Item[items.length];
        System.arraycopy(items, 0, copy, 0, items.length);
        return copy;
    }

    public Item[] removeAllItems() {
        Item[] items = getItemsCopy();

        for (int i = getInventorySize() - 1; i >= 0; --i) {
            setItem(i, null);
        }

        return items;
    }

    public void addAttackSpeed(float amount) {
        attackSpeedAdd += amount;
    }

    public void addChanceToMiss(float amount) {
        chanceToMiss += amount;
    }

    public float getChanceToMiss() {
        return chanceToMiss;
    }

    public void setChanceToMiss(float chanceToMiss) {
        this.chanceToMiss = chanceToMiss;
    }

    public void addItemChance(float amount) {
        itemChance += amount;
    }

    public void addItemQuality(float amount) {
        itemQuality += amount;
    }

    public void addLuck(float amount) {
        luck += amount;
    }

    public void kill(Creep creep) {
        if (!creep.isDead()) {
            creep.setHealth(0);
            onKill.dispatch(creep);
        }
    }

    public void addExperienceModifier(float amount) {
        experienceModifier += amount;
    }
}
