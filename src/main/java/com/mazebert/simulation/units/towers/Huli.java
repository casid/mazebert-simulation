package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Huli extends Tower {

    public Huli() {
        setBaseCooldown(1.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.9f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Banana, 11.8f));
        addAbility(new HuliStun());
        addAbility(new HuliEat());
        addAbility(new HuliMojo());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.98f;
    }

    @Override
    public String getName() {
        return "Huli the Monkey";
    }

    @Override
    public String getDescription() {
        return "Huli is the king of the jungle. Adored by women, feared by his enemies.";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 48;
    }

    @Override
    public String getSinceVersion() {
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "huli_the_monkey";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -6;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Bananas:";
        bonus.value = "" + getAbility(HuliEat.class).getBananasEaten();
    }
}
