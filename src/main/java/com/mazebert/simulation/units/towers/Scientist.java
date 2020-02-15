package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Scientist extends Tower {

    public Scientist() {
        setBaseCooldown(2.5f);
        setBaseRange(2);
        setAttackType(AttackType.Ber);
        setStrength(0.55f);
        setDamageSpread(0.3f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new ScientistExperience());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.ChemicalPotion, 10));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Scientist";
    }

    @Override
    public String getDescription() {
        return "This scientist dedicated his life to the research of creeps.";
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
    public int getImageOffsetOnCardY() {
        return -6;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }

    @Override
    public String getModelId() {
        return "scientist";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.5f;
    }
}
