package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;
import com.mazebert.simulation.units.potions.MeadAbility;

public strictfp class Viking extends Tower {

    public Viking() {
        setBaseCooldown(3.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.89f);
        setDamageSpread(0.16f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new ProjectileDamageAbility(ProjectileViewType.Axe, 6));
        addAbility(new VikingMultishot());
        addAbility(new VikingMead());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.1f;
    }

    @Override
    public String getName() {
        return "Holgar the Horrible";
    }

    @Override
    public String getDescription() {
        return "Holgar is the terror of the seven seas with a slight alcohol problem.";
    }

    @Override
    public String getAuthor() {
        return "Holger Herbricht";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }

    @Override
    public String getModelId() {
        return "viking";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Mead:";
        bonus.value = "" + getConsumedMeadCount();
    }

    private int getConsumedMeadCount() {
        MeadAbility meadPotionAbility = getAbility(MeadAbility.class);
        if (meadPotionAbility == null) {
           return 0;
        } else {
            return meadPotionAbility.getStackCount();
        }
    }
}
