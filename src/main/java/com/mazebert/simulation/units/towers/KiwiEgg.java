package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class KiwiEgg extends Tower {

    private final KiwiEggHatch hatch;

    public KiwiEgg() {
        setBaseCooldown(Balancing.MAX_COOLDOWN);
        setBaseRange(1);
        setAttackType(AttackType.Ber);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Unknown);
        setElement(Element.Nature);

        addAbility(hatch = new KiwiEggHatch());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "Kiwi Egg";
    }

    @Override
    public String getDescription() {
        return "A rare gift of Nature.\n(does not attack)";
    }

    @Override
    public String getAuthor() {
        return "New Zealand";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 99;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getModelId() {
        return "kiwi_egg";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Breeding:";
        bonus.value = hatch.getRounds() + "/" + KiwiEggHatch.ROUNDS;
    }
}
