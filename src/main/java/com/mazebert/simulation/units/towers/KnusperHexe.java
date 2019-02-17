package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class KnusperHexe extends Tower {

    private final KnusperHexeEat eat;

    public KnusperHexe() {
        setBaseCooldown(2.4f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.7f);
        setDamageSpread(0.6f);
        setGender(Gender.Female);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.FrogSpit, 11.8f));
        addAbility(eat = new KnusperHexeEat());
        addAbility(new KnusperHexeAura());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.81f;
    }

    @Override
    public String getName() {
        return "Knusperhexe";
    }

    @Override
    public String getDescription() {
        return "Nibble, nibble, gnaw.";
    }

    @Override
    public int getItemLevel() {
        return 72;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.6";
    }

    @Override
    public String getModelId() {
        return "the_witch";
    }

    @Override
    public String getAuthor() {
        return "Karolin Herbricht";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Eaten:";
        bonus.value = "" + getCreepsEaten();
    }

    public int getCreepsEaten() {
        return eat.getCreepsEaten();
    }
}
