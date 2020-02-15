package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class Hitman extends Tower {
    public Hitman() {
        setBaseCooldown(5.0f);
        setBaseRange(6.0f);
        setAttackType(AttackType.Fal);
        setStrength(1.5f);
        setDamageSpread(1.0f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/gun-gunshot-02.mp3"));
        addAbility(new InstantDamageAbility());
        addAbility(new HitmanHeadshot());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Hitman";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getModelId() {
        return "hitman";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "This professional killer prefers to attack from the distance, hidden in shadows.";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.3f;
    }
}
