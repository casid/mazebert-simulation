package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Candle extends Tower {

    public Candle() {
        setBaseCooldown(2.4f);
        setBaseRange(2.0f);
        setAttackType(AttackType.All);
        setStrength(0.7f);
        setDamageSpread(0.0f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Fireball, 15));
        addAbility(new CandleAttractionAura());
        addAbility(new CandleAirDamage());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.91f;
    }

    @Override
    public String getName() {
        return "Candle";
    }

    @Override
    public String getDescription() {
        return "It attracts flying critters!";
    }

    @Override
    public String getAuthor() {
        return "t4c1";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "candle";
    }
}
