package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.abilities.VikingAbility;

public strictfp class Thor extends Tower {

    public Thor() {
        setBaseCooldown(60.0f);
        setBaseRange(4.0f);
        setAttackType(AttackType.Vex);
        setStrength(1.6f);
        setDamageSpread(0.2f);
        setGender(Gender.Male);
        setElement(Element.Light);

        ThorAttack thorAttack = new ThorAttack();

        addAbility(thorAttack);
        addAbility(new InstantDamageAbility());
        addAbility(new ThorMjoelnir());
        addAbility(new ThorHoldBack(thorAttack));
        addAbility(new VikingAbility(false));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, true, 2021)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 9.722125f;
    }

    @Override
    public String getName() {
        return "Thor";
    }

    @Override
    public String getDescription() {
        return "God of Thunder.";
    }

    @Override
    public int getItemLevel() {
        return 70;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "2.4";
    }

    @Override
    public String getModelId() {
        return "thor";
    }

    @Override
    public String getAuthor() {
        return "FuzzyEuk";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }
}
