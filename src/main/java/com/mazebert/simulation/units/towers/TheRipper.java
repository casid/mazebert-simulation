package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class TheRipper extends Tower {

    private final TheRipperBloodThirst bloodThirst;

    public TheRipper() {
        setBaseCooldown(3.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.7f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/slash.mp3"));
        addAbility(new InstantDamageAbility());
        addAbility(bloodThirst = new TheRipperBloodThirst());
        addAbility(new TheRipperKillingSpree());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v20, false, 2020, "Killing Spree speed bonus increased from 100% to 160%.", "Killing Spree speed bonus per level increased from 2% to 3%."),
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "The Ripper";
    }

    @Override
    public String getDescription() {
        return "Call me Jack.";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "1.1";
    }

    @Override
    public String getModelId() {
        return "the_ripper";
    }

    @Override
    public String getAuthor() {
        return "syotos";
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Thirsts:";
        bonus.value = "" + bloodThirst.getAmount();
    }
}
