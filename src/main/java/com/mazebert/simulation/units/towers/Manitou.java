package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class Manitou extends Tower {

    public Manitou() {
        setBaseCooldown(5.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new ManitouAura());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021, "Aura buff is doubled for Nature towers."),
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Aura range is always the same as Manitous' range."),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.6f;
    }

    @Override
    public String getName() {
        return "Manitou";
    }

    @Override
    public String getDescription() {
        return "The Great Spirit of the West.\n(does not attack)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }

    @Override
    public String getModelId() {
        return "manitou";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }
}
