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

public strictfp class KingArthur extends Tower {

    public KingArthur() {
        setBaseCooldown(4.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.95f);
        setDamageSpread(0.4f);
        setGender(Gender.Male);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/abyss_king_attack.mp3"));
        addAbility(new InstantDamageAbility());
        addAbility(new KingArthurExcalibur());
        if (Sim.context().version >= Sim.vRoCEnd) {
            addAbility(new KingArthurGuardAura(getBaseRange()));
            addAbility(new KingArthurDamagePerLevel());
        } else {
            addAbility(new GuardAura(getBaseRange(), Sim.context().version >= Sim.vDoLEnd ? 12 : 6));
        }
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021, "Now gains 1% damage per level."),
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Base damage for every other guard in range increased from 6 to 12."),
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.61f;
    }

    @Override
    public String getName() {
        return "King Arthur";
    }

    @Override
    public String getDescription() {
        return "I'm the guardian of the light.\nI'll defeat all evil!\n(Counts as a Guard)";
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "king_arthur";
    }

    @Override
    public String getAuthor() {
        return "SnoreFox";
    }
}
