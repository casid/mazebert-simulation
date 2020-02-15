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

public strictfp class HerbWitch extends Tower {

    public HerbWitch() {
        setBaseCooldown(1.2f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.6f);
        setDamageSpread(0.1f);
        setGender(Gender.Female);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.FrogSpit, 11.8f));
        addAbility(new HerbWitchAura());
        addAbility(new HerbWitchWisdom());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.05f;
    }

    @Override
    public String getName() {
        return "Herb Witch";
    }

    @Override
    public String getDescription() {
        return "The potions of this witch give strength to her allies.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
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
        return "herb_witch";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -6;
    }
}
