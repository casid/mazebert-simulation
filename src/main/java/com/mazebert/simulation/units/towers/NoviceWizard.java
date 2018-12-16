package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class NoviceWizard extends Tower {

    public NoviceWizard() {
        setBaseCooldown(2.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.5f);
        setDamageSpread(0.7f);
        setGender(Gender.Male);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Spell, 13.0f));
        addAbility(new NoviceWizardSpell());
    }

    @Override
    public String getName() {
        return "Twisted Novice Wizard";
    }

    @Override
    public String getDescription() {
        return "I'll kill you. I'll kill you all!\nAh-hahahah!";
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
        return "1.0";
    }

    @Override
    public String getModelId() {
        return "novice_wizard";
    }

    @Override
    public String getAuthor() {
        return "syotos";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.05f;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }
}
