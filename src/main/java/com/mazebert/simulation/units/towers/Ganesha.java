package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class Ganesha extends Tower {

    public Ganesha() {
        setBaseCooldown(1.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new GaneshaExperienceAura());
        addAbility(new GaneshaLevelAura());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.21f;
    }

    @Override
    public String getName() {
        return "Ganesha";
    }

    @Override
    public String getDescription() {
        return "The deity of intellect and wisdom.\n(does not attack)";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 72;
    }

    @Override
    public String getSinceVersion() {
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "ganesha";
    }
}
