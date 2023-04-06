package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class Hydra extends Tower {

    private final HydraMultishot multishot;

    public Hydra() {
        setBaseCooldown(1.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.3f);
        setDamageSpread(0.25f);
        setGender(Gender.Unknown);
        setElement(Element.Darkness);

        addAbility(new HydraMultishotDescription());
        addAbility(multishot = new HydraMultishot());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vToC, true, 2023)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.9f;
    }

    @Override
    public String getName() {
        return "Hydra";
    }

    @Override
    public String getDescription() {
        return "Hydra has 3 heads and can make 3 attacks at a time.";
    }

    @Override
    public int getItemLevel() {
        return 32;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "2.7";
    }

    @Override
    public String getModelId() {
        return "hydra"; // TODO
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Heads:";
        bonus.value = "" + getHeadCount();
    }

    @Override
    public String getAuthor() {
        return "MG Masi & FuzzyEuk";
    }

    int getHeadCount() {
        return multishot.getHeadCount();
    }
}
