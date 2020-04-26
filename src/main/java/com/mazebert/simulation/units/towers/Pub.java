package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Pub extends Tower {

    public Pub() {
        setBaseCooldown(2.8f);
        setBaseRange(2);
        setAttackType(AttackType.Ber);
        setStrength(0.64f);
        setDamageSpread(0.4f);
        setGender(Gender.Unknown);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Beer, 6));
        addAbility(new PubAura());
        if (Sim.context().version >= Sim.v11) {
            addAbility(new PubParty());
        } else {
            addAbility(new PubParty10());
        }
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Aura range is always the same as Pubs' range."),
                new ChangelogEntry(Sim.v11, false, 2019, "Rebalanced Pub Party"),
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Irish Pub";
    }

    @Override
    public String getDescription() {
        return "Serves the best Guinness in town.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -2;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getModelId() {
        return "pub";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.05f;
    }
}
