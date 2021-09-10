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

public strictfp class Loki extends Tower {

    public Loki() {
        setBaseCooldown(4.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(1.0f);
        setDamageSpread(0.4f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new LokiTransmute());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, true, 2021)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.6f;
    }

    @Override
    public String getName() {
        return "Loki";
    }

    @Override
    public String getDescription() {
        return "The trickster god";
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
        return "loki";
    }
}
