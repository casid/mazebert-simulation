package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.listeners.OnAttack;
import com.mazebert.simulation.units.CooldownUnit;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;

public strictfp abstract class Tower extends Unit implements CooldownUnit, Card {

    public final OnAttack onAttack = new OnAttack();

    private int level;
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
    private Element element;
    private Gender gender;
    private AttackType attackType;

    @Override
    public void hash(Hash hash) {
        super.hash(hash);
        hash.add(level);
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
        hash.add(element);
        hash.add(gender);
        hash.add(attackType);
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
        return multicrit;
    }

    public void setMulticrit(int multicrit) {
        this.multicrit = multicrit;
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
            this.level = level;
            setBaseDamage(Balancing.getBaseDamage(this));
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

    protected float getGoldCostFactor() {
        return 1.0f;
    }

    public void addCritChance(float critChance) {
        this.critChance += critDamage;
    }

    public void addCritDamage(float critDamage) {
        this.critDamage += critDamage;
    }
}
