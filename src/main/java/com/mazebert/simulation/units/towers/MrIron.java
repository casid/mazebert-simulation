package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Gender;

public strictfp class MrIron extends Tower {

    public MrIron() {
        setBaseCooldown(2.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.All);
        setStrength(0.2f);
        setDamageSpread(0.5f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new MrIronAttack());
        addAbility(new MrIronLightning());
        addAbility(new MrIronConstruct());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.8f;
    }

    @Override
    public String getName() {
        return "Mr. Iron";
    }

    @Override
    public String getDescription() {
        return "A true hero.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getModelId() {
        return "iron_man";
    }

    @Override
    public String getAuthor() {
        return "Rafael Flaig";
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Constr. time:";
        bonus.value = Sim.context().formatPlugin.seconds(getAbility(MrIronConstruct.class).getCooldown());
    }
}
