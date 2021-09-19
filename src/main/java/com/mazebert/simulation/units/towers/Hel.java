package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.VikingAbility;

public strictfp class Hel extends Tower {

    private final HelAura helAura;

    public Hel() {
        setBaseCooldown(1.0f);
        setBaseRange(4.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Female);
        setElement(Element.Darkness);

        helAura = new HelAura();
        addAbility(helAura);
        addAbility(new VikingAbility(false));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, true, 2021)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "Hel";
    }

    @Override
    public String getDescription() {
        return "Ruler of Helheim.\n(does not attack)";
    }

    @Override
    public int getItemLevel() {
        return 95;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "2.4";
    }

    @Override
    public String getModelId() {
        return "hel";
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Helheim:";
        bonus.value = "" + helAura.getHelheimPopulation();
    }

    @Override
    public boolean isForgeable() {
        return false;
    }
}
