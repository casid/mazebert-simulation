package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;

public strictfp class MrIron extends Tower {

    private final int version = Sim.context().version;

    public MrIron() {
        setBaseCooldown(2.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.All);
        setStrength(0.2f);
        setDamageSpread(0.5f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new MrIronAttack());
        addAbility(new MrIronLightning());
        addAbility(new MrIronConstruct());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.8f;
    }

    @Override
    public String getName() {
        return "Mr. Iron";
    }

    @Override
    public String getDescription() {
        return "A true hero.";
    }

    @Override
    public Rarity getRarity() {
        if (version >= Sim.v13) {
            return Rarity.Unique;
        }
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        if (version >= Sim.v13) {
            return 67;
        }
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getModelId() {
        return "iron_man";
    }

    @Override
    public String getAuthor() {
        return "Rafael Flaig";
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Constr. time:";
        bonus.value = format.seconds(getAbility(MrIronConstruct.class).getCooldown());
    }
}
