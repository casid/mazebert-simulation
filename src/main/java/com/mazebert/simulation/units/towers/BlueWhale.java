package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.maps.Terrain;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class BlueWhale extends Tower {

    public BlueWhale() {
        setBaseCooldown(5.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.7f);
        setDamageSpread(0.8f);
        setGender(Gender.Female);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.FrogSpit, 11.8f)); // TODO new projectile
        addAbility(new BlueWhaleSplash());
        addAbility(new BlueWhaleStun());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(new ChangelogEntry(Sim.vToC, true, 2023));
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.1f;
    }

    @Override
    public String getName() {
        return "Blue Whale";
    }

    @Override
    public String getDescription() {
        return "A gentle giant.";
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
    public String getSinceVersion() {
        return "2.7";
    }

    @Override
    public String getModelId() {
        return "herb_witch"; // TODO
    }

    @Override
    public Terrain getTerrain() {
        return Terrain.Water;
    }
}
