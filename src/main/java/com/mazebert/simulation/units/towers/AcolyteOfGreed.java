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

public strictfp class AcolyteOfGreed extends Tower {

    public AcolyteOfGreed() {
        setBaseCooldown(1.6f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Vex);
        setStrength(1.4f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Bandage, 11.8f));
        addAbility(new AcolyteOfGreedBattlecry());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.58f;
    }

    @Override
    public String getName() {
        return "Acolyte of Greed";
    }

    @Override
    public String getDescription() {
        return "The greeeeed speaks to me!";
    }

    @Override
    public int getItemLevel() {
        return 87;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getModelId() {
        return "acolyte_of_greed";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 6;
    }
}
