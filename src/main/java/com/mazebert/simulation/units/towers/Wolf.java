package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.WolfSystem;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class Wolf extends Tower {

    private final WolfSystem wolfSystem = Sim.context().wolfSystem;

    public Wolf() {
        setBaseCooldown(1.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.9f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new WolfAura());
        addAbility(new WolfPack());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.12f;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Status:";
        if (isAlphaWolf()) {
            bonus.value = "Alpha wolf";
        } else {
            bonus.value = "Pack wolf";
        }
    }

    public boolean isAlphaWolf() {
        return wolfSystem.isAlphaWolf(this);
    }

    @Override
    public String getName() {
        return "Wolf";
    }

    @Override
    public String getDescription() {
        return "All for one, and one for all.";
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
        return "wolf";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }
}