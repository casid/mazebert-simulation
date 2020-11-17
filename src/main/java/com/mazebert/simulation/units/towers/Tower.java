package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.CooldownUnit;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.VikingAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp abstract class Tower extends Unit implements CooldownUnit, Card, OnKillListener {

    private static final int MIN_INVENTORY_SIZE = 4;
    private static final int MAX_INVENTORY_SIZE = 6;

    public final OnAttack onAttack = new OnAttack();
    public final OnDamage onDamage = new OnDamage();
    public final OnChain onChain = new OnChain();
    public final OnKill onKill = new OnKill();
    public final OnMiss onMiss = new OnMiss();
    public final OnExperienceChanged onExperienceChanged = new OnExperienceChanged();
    public final OnLevelChanged onLevelChanged = new OnLevelChanged();
    public final OnRangeChanged onRangeChanged = new OnRangeChanged();
    public final OnGenderChanged onGenderChanged = new OnGenderChanged();
    public final OnItemEquipped onItemEquipped = new OnItemEquipped();
    public final OnInventorySizeChanged onInventorySizeChanged = new OnInventorySizeChanged();
    public final OnPotionConsumed onPotionConsumed = new OnPotionConsumed();
    public final OnPotionEffectivenessChanged onPotionEffectivenessChanged = new OnPotionEffectivenessChanged();
    public final OnAbilityActivated onAbilityActivated = new OnAbilityActivated();
    public final OnAbilityReady onAbilityReady = new OnAbilityReady();
    public final OnTowerReplaced onTowerReplaced = new OnTowerReplaced();

    protected final FormatPlugin format = Sim.context().formatPlugin;
    protected final int version = Sim.context().version;

    private int level;
    private float experience;
    private float strength = 1.0f;
    private float baseCooldown = Float.MAX_VALUE;
    private float attackSpeedAdd;
    private transient float cooldown;
    private float baseRange = 1.0f;
    private float addedRange = 0.0f;
    private transient float range;
    private float damageSpread; // [0, 1], 0=constant damage, 1=max spread damage
    private float minBaseDamage;
    private float maxBaseDamage;
    private float addedRelativeBaseDamage;
    private float addedAbsoluteBaseDamage;
    private float critChance = Balancing.STARTING_CRIT_CHANCE;
    private float critDamage = Balancing.STARTING_CRIT_DAMAGE;
    private int multicrit = 1;
    private float damageAgainstBer = 1;
    private float damageAgainstFal = 1;
    private float damageAgainstVex = 1;
    private float damageAgainstZod = 1;
    private float damageAgainstAir = 1;
    private float damageAgainstBosses = 1;
    private float armorPenetration;
    private float chanceToMiss;
    private float luck = 1.0f; // factor 1 is regular luck of every tower
    private int multiluck = 1;
    private float itemChance = 1.0f; // 1.0 is 100% item chance (normal, not good not bad)
    private float itemQuality = 1.0f; // 1.0 is 100% item quality (normal, not good not bad)
    private float potionEffectiveness = 1.0f; // 1.0 is 100% potion effect (normal, not good not bad)
    private int dealNoDamage;
    private Element element;
    private Gender gender;
    private AttackType attackType;
    private float experienceModifier = 1.0f; // factor 1 is regular experience gain
    private float goldModifier = 1.0f; // factor 1 is regular gold gain
    private float eldritchCardModifier = 1.0f; // factor 1 is regular effect
    private double bestHit;
    private double totalDamage;
    private int kills;
    private int inventorySize = MIN_INVENTORY_SIZE;

    private transient TowerType type;
    private transient final Item[] items = new Item[MAX_INVENTORY_SIZE];

    public Tower() {
        onKill.add(this);
        updateCooldown();
        updateRange();
    }

    @SuppressWarnings("Duplicates")
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
        hash.add(damageAgainstBer);
        hash.add(damageAgainstFal);
        hash.add(damageAgainstVex);
        hash.add(damageAgainstAir);
        hash.add(armorPenetration);
        hash.add(chanceToMiss);
        hash.add(luck);
        hash.add(itemChance);
        hash.add(itemQuality);
        hash.add((Hashable) element);
        hash.add(gender);
        hash.add(attackType);
        hash.add(experienceModifier);
        hash.add(goldModifier);
        hash.add(bestHit);
        hash.add(totalDamage);
        hash.add(kills);
        hash.add(inventorySize);
    }

    public float getBaseCooldown() {
        return baseCooldown;
    }

    public void setBaseCooldown(float baseCooldown) {
        this.baseCooldown = baseCooldown;
        updateCooldown();
    }

    private void updateCooldown() {
        cooldown = getCooldown(baseCooldown);
    }

    public float getBaseRange() {
        return baseRange;
    }

    public void setBaseRange(float baseRange) {
        this.baseRange = baseRange;
        updateRange();
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
        if (this.gender != gender) {
            this.gender = gender;
            onGenderChanged.dispatch(this);
        }
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
        return cooldown;
    }

    public float getCooldown(float baseCooldown) {
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
        return "Andy";
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    @SuppressWarnings("unused") // by ui
    public int getImageOffsetOnCardX() {
        return 0;
    }

    @SuppressWarnings("unused") // by ui
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
        return isAbilityTriggeredByRawChance(chance);
    }

    private boolean isAbilityTriggeredByRawChance(float chance) {
        if (multiluck > 1) {
            for (int i = 0; i < multiluck; ++i) {
                if (Sim.context().randomPlugin.getFloatAbs() < chance) {
                    return true;
                }
                chance *= 0.8f;
            }
            return false;
        }
        return Sim.context().randomPlugin.getFloatAbs() < chance;
    }

    public boolean isImmobilizeAbilityTriggered(float chance, Creep target) {
        if (version >= Sim.vDoL || Sim.context().newBalancing) {
            chance -= target.getImmobilizeResistance();
        }
        return isAbilityTriggered(chance);
    }

    public boolean isImmobilizeAbilityTriggeredIgnoringLuck(float chance, Creep target) {
        if (version >= Sim.vDoL || Sim.context().newBalancing) {
            chance -= target.getImmobilizeResistance();
        }
        return Sim.context().randomPlugin.getFloatAbs() < chance;
    }

    public boolean isNegativeAbilityTriggered(float chance) {
        if (version >= Sim.vCorona) {
            if (luck > 1.0f) {
                return !isAbilityTriggeredByRawChance(1.0f - (chance / luck));
            } else {
                return !isAbilityTriggeredByRawChance((1.0f - chance) * luck);
            }
        } else if (version >= Sim.v19) {
            return !isAbilityTriggered(1.0f - chance);
        } else {
            return Sim.context().randomPlugin.getFloatAbs() * luck <= chance;
        }
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    @Override
    public void onKill(Creep target) {
        Sim.context().experienceSystem.grantExperience(this, target, false);
        ++kills;
        Wizard wizard = getWizard();
        if (wizard != null) {
            ++wizard.kills;
            if (kills > wizard.mostKills) {
                wizard.mostKills = kills;
                wizard.mostKillsTower = getType();
            }
        }
        Sim.context().lootSystem.loot(this, target);
    }

    public void addExperience(float amount) {
        float oldExperience = experience;
        setExperience(experience + amount);
        onExperienceChanged.dispatch(this, oldExperience, experience);
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

    public int getItemCount(ItemType type) {
        int count = 0;
        for (Item item : items) {
            if (item != null && item.getType() == type) {
                ++count;
            }
        }
        return count;
    }

    public void setItem(int index, Item item) {
        setItem(index, item, false);
    }

    public void setItem(int index, Item item, boolean userAction) {
        Item previousItem = items[index];
        if (previousItem != null) {
            previousItem.forEachAbility(this::removeAbility);
        }

        items[index] = item;

        if (item != null) {
            item.forEachAbility(this::addAbility);
        }

        onItemEquipped.dispatch(this, index, previousItem, item, userAction);
    }

    public void addAddedRelativeBaseDamage(float amount) {
        addedRelativeBaseDamage += amount;
    }

    public void addAddedAbsoluteBaseDamage(float amount) {
        addedAbsoluteBaseDamage += amount;
    }

    public void addInventorySize(int amount) {
        inventorySize += amount;
        if (amount < 0) {
            int startIndex = version >= Sim.v19 ? getInventorySize() : getInventorySize() - 1;
            for (int i = startIndex; i < MAX_INVENTORY_SIZE; ++i) {
                Item item = getItem(i);
                if (item != null) {
                    setItem(i, null);
                    getWizard().itemStash.add(item.getType());
                }
            }
        }
        onInventorySizeChanged.dispatch(this);
    }

    public int getInventorySize() {
        if (inventorySize < MIN_INVENTORY_SIZE) {
            return MIN_INVENTORY_SIZE;
        }
        if (inventorySize > MAX_INVENTORY_SIZE) {
            return MAX_INVENTORY_SIZE;
        }
        return inventorySize;
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
        double averageBaseDamage = calculateAverageBaseDamageForDisplay();
        averageBaseDamage += addedAbsoluteBaseDamage;
        averageBaseDamage = averageBaseDamage * (1.0 + addedRelativeBaseDamage);
        double damage = averageBaseDamage;

        // Calculate average crit amount and damage.
        double critChance = this.critChance;
        int multicrit = getMulticrit();
        for (int i = 0; i < multicrit; ++i) {
            if (critChance > 0.0) {
                // Add average critical strike damage to the final damage.
                damage += averageBaseDamage * critDamage * StrictMath.min(1.0, critChance);

                // Reduce crit chance for next multicrit by 20%.
                critChance *= 0.8;
            } else {
                break;
            }
        }

        return damage;
    }

    public float getItemChance() {
        return itemChance;
    }

    public float getItemQuality() {
        return itemQuality;
    }

    private Item[] getItemsCopy() {
        Item[] copy = new Item[getInventorySize()];
        System.arraycopy(items, 0, copy, 0, copy.length);
        return copy;
    }

    public Item[] removeAllItems() {
        Item[] items = getItemsCopy();

        for (int i = getInventorySize() - 1; i >= 0; --i) {
            setItem(i, null);
        }

        return items;
    }

    public void removeItem(ItemType type) {
        for (int i = getInventorySize() - 1; i >= 0; --i) {
            if (items[i] != null && items[i].getType() == type) {
                setItem(i, null);

                if (version >= Sim.vRoC) {
                    return;
                }
            }
        }
    }

    public void addAttackSpeed(float amount) {
        attackSpeedAdd += amount;
        updateCooldown();
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
        kill(creep, false);
    }

    public void kill(Creep creep, boolean notifyHealth) {
        if (creep.isPartOfGame() && !creep.isImmortal()) {
            creep.setHealth(this, 0, notifyHealth);
            onKill.dispatch(creep);
        }
    }

    public float getGoldModifier() {
        return goldModifier;
    }

    public void setGoldModifier(float goldModifier) {
        this.goldModifier = goldModifier;
    }

    public double getBestHit() {
        return bestHit;
    }

    public void setBestHit(double bestHit) {
        this.bestHit = bestHit;
    }

    public double getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(double totalDamage) {
        this.totalDamage = totalDamage;
    }

    public void addExperienceModifier(float amount) {
        experienceModifier += amount;
    }

    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Custom bonus:";
        bonus.value = "-";
    }

    public final void addRange(float amount) {
        addedRange += amount;
        updateRange();
        onRangeChanged.dispatch(this);
    }

    private void updateRange() {
        range = StrictMath.max(1, baseRange + addedRange);
    }

    public final float getRange() {
        return range;
    }

    public int getTilesInRange() {
        int range = (int)getRange();
        range = 2 * range + 1;
        return range * range;
    }

    public float getDamageAgainstBer() {
        return damageAgainstBer;
    }

    public float getDamageAgainstFal() {
        return damageAgainstFal;
    }

    public float getDamageAgainstVex() {
        return damageAgainstVex;
    }

    public float getDamageAgainstZod() {
        return damageAgainstZod;
    }

    public void addDamageAgainstBer(float amount) {
        damageAgainstBer += amount;
    }

    public void addDamageAgainstFal(float amount) {
        damageAgainstFal += amount;
    }

    public void addDamageAgainstVex(float amount) {
        damageAgainstVex += amount;
    }

    public void addDamageAgainstZod(float amount) {
        damageAgainstZod += amount;
    }

    public float getDamageAgainstAir() {
        return damageAgainstAir;
    }

    public void setDamageAgainstAir(float damageAgainstAir) {
        this.damageAgainstAir = damageAgainstAir;
    }

    public void addGoldModifer(float amount) {
        this.goldModifier += amount;
    }

    public void addDamageAgainstAir(float amount) {
        this.damageAgainstAir += amount;
    }

    public float getDamageAgainstBosses() {
        return damageAgainstBosses;
    }

    public void addDamageAgainstBosses(float amount) {
        this.damageAgainstBosses += amount;
    }

    public void addArmorPenetration(float amount) {
        this.armorPenetration += amount;
    }

    public float getArmorPenetration() {
        return armorPenetration;
    }

    @Override
    public TowerType getType() {
        if (type == null) {
            type = TowerType.forClass(getClass());
        }
        return type;
    }

    public void addPotionEffectiveness(float potionEffect) {
        this.potionEffectiveness += potionEffect;
        onPotionEffectivenessChanged.dispatch(this);
    }

    public float getPotionEffectiveness() {
        return potionEffectiveness;
    }

    public boolean isViking() {
        return this instanceof Viking || getAbility(VikingAbility.class) != null;
    }

    public void addDealNoDamage(int amount) {
        dealNoDamage += amount;
    }

    public boolean isDealNoDamage() {
        return dealNoDamage > 0;
    }

    public void addMultiluck(int amount) {
        multiluck += amount;
    }

    public void addEldritchCardModifier(float amount) {
        eldritchCardModifier += amount;
    }

    public float getEldritchCardModifier() {
        return eldritchCardModifier;
    }
}
