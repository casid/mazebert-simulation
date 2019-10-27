package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Tinker extends Tower {

    public Tinker() {
        setBaseCooldown(3.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.5f);
        setDamageSpread(0.25f);
        setGender(Gender.Male);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Wood, 11.8f));
        addAbility(new TinkerAura());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.15f;
    }

    @Override
    public String getName() {
        return "Tinker";
    }

    @Override
    public String getDescription() {
        return "Likes to drink and share his luck.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "tinker";
    }

    @Override
    public String getAuthor() {
        return "Cayenne";
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Luck:";
        bonus.value = format.percentWithSignAndUnit(getAbility(TinkerAura.class).calculateCurrentBonus());
    }
}