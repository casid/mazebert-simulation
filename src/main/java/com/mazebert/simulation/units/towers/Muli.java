package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Muli extends Tower {
    private final MuliAttack attack;

    public Muli() {
        setBaseCooldown(0.7f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(2.6f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(attack = new MuliAttack());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Banana, 11.8f));
        addAbility(new HuliStun()); // Muli has the same invisible stun ability like his brother Huli!
        addAbility(new MuliBro());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.64f;
    }

    @Override
    public String getName() {
        return "Muli the Evil Twin";
    }

    @Override
    public String getDescription() {
        return "Muli is the brother of Huli, kidnapped by scientists when he was a baby.";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 81;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getModelId() {
        return "muli_the_monkey";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -6;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Bananas:";
        int bananas = attack.getBananas();
        if (bananas > 999) {
            bonus.value = ">999";
        } else {
            bonus.value = "" + bananas;
        }
    }

    public void addBananas(int amount) {
        attack.addBananas(amount);
    }
}
