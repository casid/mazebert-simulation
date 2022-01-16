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

public strictfp class PocketThief extends Tower {

    public PocketThief() {
        setBaseCooldown(2f);
        setBaseRange(3);
        setAttackType(AttackType.Vex);
        setStrength(0.8f);
        setDamageSpread(0.2f);
        setGender(Gender.Female);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new PocketThiefGold());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Knive, 12));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Pocket Thief";
    }

    @Override
    public String getDescription() {
        if (getGender() == Gender.Female) {
            return "She's learned how to survive in the shadows of downtown.";
        } else {
            return "He's learned how to survive in the shadows of downtown.";
        }
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 22;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }

    @Override
    public String getModelId() {
        return "thief";
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.95f;
    }
}
