package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class TrainingHologram extends Tower {

    public TrainingHologram() {
        setBaseCooldown(1.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.All);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new TrainingHologramSpawn());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getName() {
        return "Training Hologram";
    }

    @Override
    public String getDescription() {
        return "A magical training device.\n(does not attack)";
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
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "training_hologram";
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.7f;
    }

    @Override
    public String getAuthor() {
        return "sweisdapro";
    }
}
