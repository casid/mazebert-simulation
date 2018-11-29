package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class PermanentCritWithLevelBonusAbility extends StackableAbility<Tower> {
    private CritChanceWithLevelBonusAbility chance;
    private CritDamageWithLevelBonusAbility damage;

    public PermanentCritWithLevelBonusAbility(float chanceBonus, float chanceBonusPerLevel, float damageBonus, float damageBonusPerLevel) {
        chance = new CritChanceWithLevelBonusAbility(chanceBonus, chanceBonusPerLevel);
        damage = new CritDamageWithLevelBonusAbility(damageBonus, damageBonusPerLevel);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        chance.init(unit);
        damage.init(unit);
    }

    @Override
    protected void dispose(Tower unit) {
        chance.dispose(unit);
        damage.dispose(unit);
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        chance.setStackCount(getStackCount());
        damage.setStackCount(getStackCount());
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Be more critical!";
    }

    @Override
    public String getDescription() {
        return "The crit damage of the carrier is permanently increased by " + format.percent(damage.bonus) + "%. The crit chance is permanently increased by " + format.percent(chance.bonus) + "%";
    }

    @Override
    public String getLevelBonus() {
        return damage.getLevelBonus() + "\n" + chance.getLevelBonus();
    }
}
