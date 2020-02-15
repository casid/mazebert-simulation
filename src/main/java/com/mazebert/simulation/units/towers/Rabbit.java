package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Rabbit extends Tower {
    public Rabbit() {
        setBaseCooldown(2.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.66f);
        setDamageSpread(0.25f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new RabbitMultishot());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Carrot, 11.8f));
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.91f;
    }

    @Override
    public String getName() {
        return "Baby Rabbit";
    }

    @Override
    public String getDescription() {
        return "Baby Rabbit is cute and likes to share his carrots.";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "rabbit";
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Carrots:";
        bonus.value = "" + getAbility(AttackAbility.class).getTargets();
    }
}
