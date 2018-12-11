package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Satellite extends Tower {
    public Satellite() {
        setBaseCooldown(2.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(0);
        setDamageSpread(0.2f);
        setGender(Gender.Unknown);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new SatelliteDamage());
        addAbility(new SatelliteCosts());
    }

    @Override
    public void setBaseDamage(float baseDamage) {
        Wizard wizard = getWizard();
        if (wizard != null) {
            baseDamage = (float)StrictMath.sqrt(wizard.gold);
        }
        super.setBaseDamage(baseDamage);
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "Blofeld Laser Satellite";
    }

    @Override
    public String getDescription() {
        return "The best technology money can buy.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 75;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }

    @Override
    public String getModelId() {
        return "satellite";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 12;
    }
}
