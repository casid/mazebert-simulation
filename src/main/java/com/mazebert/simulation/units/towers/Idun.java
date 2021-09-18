package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.VikingAbility;

public strictfp class Idun extends Tower {

    public Idun() {
        setBaseCooldown(1.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Female);
        setElement(Element.Nature);

        addAbility(new IdunReviveAura());
        addAbility(new IdunMaxLevelAura());
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
        return 1.33f;
    }

    @Override
    public String getName() {
        return "Idun";
    }

    @Override
    public String getDescription() {
        return "Goddess of Eternal Youth.\n(does not attack)";
    }

    @Override
    public int getItemLevel() {
        return 92;
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
        return "idun";
    }
}
