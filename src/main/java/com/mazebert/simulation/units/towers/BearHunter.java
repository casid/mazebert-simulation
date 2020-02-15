package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class BearHunter extends Tower {

    public BearHunter() {
        setBaseCooldown(5.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.45f);
        setDamageSpread(0.1f);
        setDamageAgainstAir(0.3f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new BearHunterPlaceTrap());
        addAbility(new BearHunterSpeed());
        addAbility(new BearHunterSplash());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.98f;
    }

    @Override
    public String getName() {
        return "Bear Hunter";
    }

    @Override
    public String getDescription() {
        return "This sneaky old trapper knows how to survive in the wild.";
    }

    @Override
    public String getAuthor() {
        return "Daniel Gerhardt";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 45;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getModelId() {
        return "bearhunter";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -10;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Active traps:";
        bonus.value = "" + getAbility(BearHunterPlaceTrap.class).getActiveTrapCount();
    }

}
