package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class BlackWidow extends Tower {
    private final int version = Sim.context().version;

    public BlackWidow() {
        setBaseCooldown(1.2f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(1.33f);
        setDamageSpread(0.3f);
        setGender(Gender.Female);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new BlackWidowAura());
        addAbility(new BlackWidowKill());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.06f;
    }

    @Override
    public String getName() {
        return "Black Widow";
    }

    @Override
    public String getDescription() {
        return "Seduce, kill, inherit.";
    }

    @Override
    public Rarity getRarity() {
        if (version == Sim.v13) {
            return Rarity.Rare; // Ooops, bad idea for one version
        }
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 63;
    }

    @Override
    public String getSinceVersion() {
        return "0.5";
    }

    @Override
    public String getAuthor() {
        return "Alex Nill";
    }

    @Override
    public String getModelId() {
        return "black_widow";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }
}
