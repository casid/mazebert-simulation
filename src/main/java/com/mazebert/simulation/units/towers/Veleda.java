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
import com.mazebert.simulation.units.abilities.VikingAbility;

public strictfp class Veleda extends Tower {

    public Veleda() {
        setBaseCooldown(12.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Vex);
        setStrength(1.0f);
        setDamageSpread(0.6f);
        setGender(Gender.Female);

        setElement(Element.Unknown);
        addAbility(new VeledaCreateProphecy());
        if (Sim.context().version >= Sim.v29) {
            addAbility(new AttackAbility());
            addAbility(new InstantDamageAbility());
            addAbility(new VikingAbility(false));
        }
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v29, true, 2021, "Veleda now attacks and damages creeps."),
                new ChangelogEntry(Sim.vRnR, true, 2021)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.33f;
    }

    @Override
    public String getName() {
        return "Veleda";
    }

    @Override
    public String getDescription() {
        return format.prophecyDescription("Weaver of Fate.\nCan equip prophecy items.");
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 7;
    }

    @Override
    public String getSinceVersion() {
        return "2.5";
    }

    @Override
    public String getModelId() {
        return "veleda";
    }

    @Override
    public boolean isProphecy() {
        return true;
    }
}
