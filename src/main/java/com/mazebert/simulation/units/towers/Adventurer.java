package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Adventurer extends Tower {

    public Adventurer() {
        setBaseCooldown(2.0f);
        setBaseRange(4.0f);
        setAttackType(AttackType.All);
        setStrength(0.9f);
        setDamageSpread(0.3f);
        setGender(Gender.Female);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new AdventurerLight());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Fireball, 15));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getName() {
        return "Adventurer";
    }

    @Override
    public String getDescription() {
        if (getGender() == Gender.Female) {
            return "Her light helps her find treasures in deep, dark caves.";
        } else {
            return "His light helps him find treasures in deep, dark caves.";
        }
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
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
        return "adventurer";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.2f;
    }

    @Override
    public String getAuthor() {
        return "TheMarine";
    }
}
