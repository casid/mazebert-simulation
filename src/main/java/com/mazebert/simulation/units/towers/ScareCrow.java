package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class ScareCrow extends Tower {
    public ScareCrow() {
        setBaseCooldown(2.8f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.6f);
        setDamageSpread(0.65f);
        setGender(Gender.Unknown);
        setElement(Element.Darkness);

        addAbility(new ScareCrowMultishot());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Crow, 10.0f));
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.98f;
    }

    @Override
    public String getName() {
        return "Scarecrow";
    }

    @Override
    public String getDescription() {
        return "Once built by farmers, Scarecrow joined the dark side and became the master of a flock of vicious crows.";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.6";
    }

    @Override
    public String getModelId() {
        return "scare_crow";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Crows:";
        bonus.value = "" + getAbility(ScareCrowMultishot.class).getCrows();
    }
}
