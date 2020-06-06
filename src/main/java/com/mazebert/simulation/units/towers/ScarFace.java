package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class ScarFace extends Tower {

    public ScarFace() {
        setBaseCooldown(3.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Ber);
        if (Sim.context().version >= Sim.vDoLEnd) {
            setStrength(0.33f);
        } else {
            setStrength(0.25f);
        }
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        ScarFaceBurst burst = new ScarFaceBurst();
        addAbility(burst);
        addAbility(new ScarFaceAttack(burst));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Increased base damage by 32%.", "Reduced chance per level from 0.3% to 0.2%."),
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.05f;
    }

    @Override
    public String getName() {
        return "Scarface";
    }

    @Override
    public String getDescription() {
        return "Say hello to my little friend!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getModelId() {
        return "sniper";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 16;
    }

    @Override
    public String getAuthor() {
        return "Nomad";
    }
}
