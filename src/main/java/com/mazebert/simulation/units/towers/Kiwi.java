package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class Kiwi extends Tower {

    public Kiwi() {
        setBaseCooldown(5.0f);
        setBaseRange(1);
        setAttackType(AttackType.Ber);
        setStrength(0.33f);
        setDamageSpread(0.1f);
        setGender(Gender.Unknown);
        setElement(Element.Nature);

        addAbility(new KiwiDamage());
        addAbility(new KiwiHaka());
        addAbility(new KiwiKick());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.0f;
    }

    @Override
    public String getName() {
        return "Kiwi";
    }

    @Override
    public String getDescription() {
        return "Ringa pakia! Uma tiraha!\nTuri whatia! Hope whai ake!";
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
        return "kiwi";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Cooldown:";
        bonus.value = format.seconds(getAbility(KiwiHaka.class).getCooldown());
    }
}
