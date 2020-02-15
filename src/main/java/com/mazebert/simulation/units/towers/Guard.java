package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.*;

public strictfp class Guard extends Tower {

    public Guard() {
        setBaseCooldown(2.0f);
        setBaseRange(2);
        setAttackType(AttackType.Ber);
        setStrength(0.95f);
        setDamageSpread(0.2f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Axe, 6));
        addAbility(new RandomGenderAbility());
        addAbility(new GuardAura(getBaseRange()));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getName() {
        return "Guard";
    }

    @Override
    public String getDescription() {
        return "THE EMPEROR PROTECTS!";
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
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "guard";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.2f;
    }

    @Override
    public String getAuthor() {
        return "Cayenne";
    }
}
