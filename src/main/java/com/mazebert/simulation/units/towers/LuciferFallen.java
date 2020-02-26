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

public strictfp class LuciferFallen extends Tower {

    public LuciferFallen() {
        setBaseCooldown(2.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.6f);
        setDamageSpread(0.5f);
        setGender(Gender.Male);
        setElement(Element.Darkness);

        if (Sim.context().version >= Sim.v20) {
            addAbility(new AttackAbility(2));
        } else {
            addAbility(new AttackAbility());
        }
        addAbility(new InstantDamageAbility());
        addAbility(new LuciferFallenLightning());
        addAbility(new LuciferFallenRestInPeace());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v20, false, 2020, "Lucifer can attack up to 2 creeps at once."),
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0;
    }

    @Override
    public String getName() {
        return "Lucifer";
    }

    @Override
    public String getDescription() {
        return "...the lower you fall.\n(Cannot be built)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 90;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "lucifer_fallen";
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }
}
