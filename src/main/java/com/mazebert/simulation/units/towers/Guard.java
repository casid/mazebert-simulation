package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.*;

public strictfp class Guard extends Tower {

    public static final float RANGE = 2;

    public Guard() {
        setBaseCooldown(2.0f);
        setBaseRange(RANGE);
        setAttackType(AttackType.Ber);
        setStrength(0.75f);
        setDamageSpread(0.2f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Axe, 6));
        addAbility(new RandomGenderAbility());
        addAbility(new GuardAura());
    }

    @Override
    public String getName() {
        return "Guard";
    }

    @Override
    public String getDescription() {
        return "THE EMPEROR PROTECTS!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.9";
    }

    @Override
    public String getModelId() {
        return "guard";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.2f;
    }

    @Override
    public String getAuthor() {
        return "Cayenne";
    }
}
