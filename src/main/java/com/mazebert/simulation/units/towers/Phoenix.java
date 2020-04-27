package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class Phoenix extends Tower {

    public Phoenix() {
        setBaseCooldown(1.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.5f);
        setDamageSpread(0.2f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new PhoenixBurn());
        addAbility(new PhoenixRebirth());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Rebirth gold cost reduced from 1000 to 500.", "Rebirth costs +500 gold per rebirth", "Rebirth damage increased from 14 to 21."),
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.98f;
    }

    @Override
    public String getName() {
        return "Phoenix";
    }

    @Override
    public String getDescription() {
        return "Born from ash, aeon after aeon.";
    }

    @Override
    public String getAuthor() {
        return "Ghul";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 48;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "phoenix";
    }
}
