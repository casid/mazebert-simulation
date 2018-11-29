package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Beaver extends Tower {

    public Beaver() {
        setBaseCooldown(2.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.7f);
        setDamageSpread(0.05f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new BeaverStun());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Wood, 14));
    }

    @Override
    public String getName() {
        return "Beaver";
    }

    @Override
    public String getDescription() {
        return "Beaver is good at two things. Sleeping and throwing wood at creeps.";
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
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "beaver";
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.95f;
    }
}
