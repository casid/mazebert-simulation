package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Solara extends Tower {

    public Solara() {
        setBaseCooldown(4.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.1f);
        setDamageSpread(0.4f);
        setGender(Gender.Female);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/fireball.mp3"));
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Fireball, 10.0f));
        addAbility(new SolaraFireball());
        addAbility(new SolaraBurn());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "Solara, The Fire Elemental";
    }

    @Override
    public String getDescription() {
        return "She is really HOT. Don't make her angry!";
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
        return "1.0";
    }

    @Override
    public String getModelId() {
        return "solara";
    }

    @Override
    public String getAuthor() {
        return "Spirrow";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -2;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Burn damage";
        bonus.value = "?";

    }
}
