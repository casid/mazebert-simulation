package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Frog extends Tower {

    public Frog() {
        setBaseCooldown(3.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.8f);
        setDamageSpread(0.4f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        if (Sim.context().version >= Sim.v20) {
            addAbility(new FrogPoisonArmorAbility());
        }
        addAbility(new FrogPoisonAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.FrogSpit, 12));
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.15f;
    }

    @Override
    public String getName() {
        return "Poisonous Frog";
    }

    @Override
    public String getDescription() {
        return "This frog likes to kill his victims slowly. His poisonous spit causes immense pain.";
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
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "frog";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }
}
