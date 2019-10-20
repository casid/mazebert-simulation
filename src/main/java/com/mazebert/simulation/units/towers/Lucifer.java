package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Lucifer extends Tower {

    public Lucifer() {
        setBaseCooldown(2.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(2.0f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Fireball, 13));
        addAbility(new LuciferSummonLightbringer());
        addAbility(new LuciferRemoveLightbringer());
    }

    @Override
    protected float getGoldCostFactor() {
        return 2.75f;
    }

    @Override
    public String getName() {
        return "Lucifer";
    }

    @Override
    public String getDescription() {
        return "todo";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 90;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "yggdrasil"; // TODO visuals
    }

    @Override
    public boolean isLight() {
        return true;
    }
}
