package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Shadow extends Tower {
    private final ShadowAdapt adapt;

    public Shadow() {
        setBaseCooldown(1.0f);
        setBaseRange(4.0f);
        setAttackType(AttackType.All);
        setStrength(1.2f);
        setDamageSpread(0.5f);
        setGender(Gender.Unknown);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Crow, 10.0f));
        addAbility(adapt = new ShadowAdapt());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.96f;
    }

    @Override
    public String getName() {
        return "Shadow";
    }

    @Override
    public String getDescription() {
        return "It can take many forms.";
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
        return "0.7";
    }

    @Override
    public String getModelId() {
        return "shadow";
    }

    @Override
    public String getAuthor() {
        return "Grunky";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "A:";
        bonus.value = "<c=#5d8b4c>" + formatAdaptionNumber(adapt.getDamageAdaptedForBer()) + "%</c>/" +
                "<c=#9da9bc>" + formatAdaptionNumber(adapt.getDamageAdaptedForFal()) + "%</c>/" +
                "<c=#760983>" + formatAdaptionNumber(adapt.getDamageAdaptedForVex()) + "%</c>";

    }

    private String formatAdaptionNumber(float adaption) {
        if (adaption < 10.0f) {
            return format.percent(adaption, 1);
        } else if (adaption < 100.0f) {
            return format.percent(0.001f * adaption, 1) + "k";
        } else {
            return format.percent(0.001f * adaption, 0) + "k";
        }
    }
}
