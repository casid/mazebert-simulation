package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class SnowGlobe extends Tower {

    public SnowGlobe() {
        setBaseCooldown(1.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.All);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new SnowGlobeCreateItem());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.35f;
    }

    @Override
    public String getName() {
        return "Snow Globe";
    }

    @Override
    public String getDescription() {
        return "There is room for a tower inside.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 83;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "snow_globe";
    }

    @Override
    public boolean isHarmful() {
        return true;
    }

    @Override
    public boolean isTransferable() {
        return Sim.context().version < Sim.vRnR;
    }
}
