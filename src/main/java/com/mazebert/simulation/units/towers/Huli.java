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

public strictfp class Huli extends Tower {

    public static final ProjectileViewType PROJECTILE_VIEW_TYPE = ProjectileViewType.Banana;
    public static final float PROJECTILE_SPEED = 11.8f;

    private final HuliAttack attack;
    private final HuliEat eat;

    public Huli() {
        setBaseCooldown(1.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.9f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(attack = new HuliAttack());
        addAbility(new ProjectileDamageAbility(PROJECTILE_VIEW_TYPE, PROJECTILE_SPEED));
        addAbility(new HuliStun());
        addAbility(eat = new HuliEat());
        addAbility(new HuliMojo());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    public void setInfluencedByMuli(boolean influenced) {
        attack.setEnabled(!influenced);
        eat.setEnabled(!influenced);
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.98f;
    }

    @Override
    public String getName() {
        return "Huli the Monkey";
    }

    @Override
    public String getDescription() {
        if (getGender() == Gender.Male) {
            return "Huli is the king of the jungle. Adored by women, feared by his enemies.";
        } else {
            return "Huli is the queen of the jungle. Adored by men, feared by her enemies.";
        }
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
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
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "huli_the_monkey";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -6;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Bananas:";
        bonus.value = "" + eat.getBananasEaten();
    }
}
