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
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Solara extends Tower {

    private final SolaraBurn burn;

    public Solara() {
        setBaseCooldown(4.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.2f);
        setDamageSpread(0.4f);
        setGender(Gender.Female);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/fireball.mp3"));
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Fireball, 10.0f));
        addAbility(new SolaraFireball());
        burn = new SolaraBurn();
        addAbility(burn);
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "Solara, The Fire Elemental";
    }

    @Override
    public String getDescription() {
        return "Solara is HOT,\nand she burns with righteous fury.";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getModelId() {
        return "solara";
    }

    @Override
    public String getAuthor() {
        return "Spirrow";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -2;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Burn damage:";
        bonus.value = format.percent(burn.getBurnDamageFactor()) + "%";
    }
}
