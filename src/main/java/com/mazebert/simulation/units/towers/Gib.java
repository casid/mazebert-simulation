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

public strictfp class Gib extends Tower {
    public Gib() {
        setBaseCooldown(1.6f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.5f);
        setDamageSpread(0.14f);
        setGender(Gender.Unknown);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Crow, 10.0f));
        addAbility(new GibFreeze());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "Gib, the Frozen Daemon";
    }

    @Override
    public String getDescription() {
        return "A daemon of frost that freezes its targets more and more.\n(Obtainable only by summoning)";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getModelId() {
        return "gib";
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }
}
