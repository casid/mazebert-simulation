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

public strictfp class Beacon extends Tower {

    public Beacon() {
        setBaseCooldown(3.4f);
        setBaseRange(1.0f);
        setAttackType(AttackType.All);
        setStrength(0.7f);
        setDamageSpread(0.32f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Fireball, 12));
        addAbility(new BeaconLevel());
        addAbility(new BeaconAura());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.27f;
    }

    @Override
    public String getName() {
        return "Beacon of Hope";
    }

    @Override
    public String getDescription() {
        return "United we stand.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 100;
    }

    @Override
    public String getSinceVersion() {
        return "2.3";
    }

    @Override
    public String getAuthor() {
        return "Kami";
    }

    @Override
    public String getModelId() {
        return "beacon";
    }
}
