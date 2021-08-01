package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class Fox extends Tower {

    public Fox() {
        setBaseCooldown(1.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.All);
        setStrength(0.75f);
        setDamageSpread(0.2f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new FoxHunt());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, false, 2021)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.37f;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Rabbits eaten:";
        bonus.value = "" + getAbility(FoxHunt.class).getRabbitsEaten();
    }

    @Override
    public String getName() {
        return "Snore Fox";
    }

    @Override
    public String getDescription() {
        return "A cunning hunter.";
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
        return "2.4";
    }

    @Override
    public String getModelId() {
        return "wolf"; // TODO
    }

    @Override
    public String getAuthor() {
        return "SnoreFox";
    }
}
