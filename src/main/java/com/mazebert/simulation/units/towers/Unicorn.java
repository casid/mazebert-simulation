package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class Unicorn extends Tower {

    public Unicorn() {
        setBaseCooldown(5.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Female);
        setElement(Element.Light);

        addAbility(new UnicornImpaleAbility());
        addAbility(new UnicornExperienceAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoC, false, 2020, "Unicorn can be sold for Unicorn Tears. Training Hologram creeps cannot kill Unicorn."),
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.75f;
    }

    @Override
    public String getName() {
        return "Rainbow Unicorn";
    }

    @Override
    public String getDescription() {
        return "The treasure at the end of the rainbow.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "unicorn";
    }
}
