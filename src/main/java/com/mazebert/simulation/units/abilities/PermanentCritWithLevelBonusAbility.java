package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class PermanentCritWithLevelBonusAbility extends StackableAbility<Tower> {
    private final CritChanceWithLevelBonusAbility chance;
    private final CritDamageWithLevelBonusAbility damage;

    public PermanentCritWithLevelBonusAbility(float chanceBonus, float chanceBonusPerLevel, float damageBonus, float damageBonusPerLevel) {
        if (Sim.context().version >= Sim.v26) {
            chance = new PermanentCritChanceWithLevelBonusAbility(chanceBonus, chanceBonusPerLevel);
            damage = new PermanentCritDamageWithLevelBonusAbility(damageBonus, damageBonusPerLevel);
        } else {
            chance = new CritChanceWithLevelBonusAbility(chanceBonus, chanceBonusPerLevel);
            damage = new CritDamageWithLevelBonusAbility(damageBonus, damageBonusPerLevel);
        }
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
        return "More Critical";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(chance.bonus) + " " + chance.getAttributeName() + ".\n" +
                format.percentWithSignAndUnit(damage.bonus) + " " + damage.getAttributeName() + ".\n" +
                format.percentWithSignAndUnit(chance.bonusPerLevel) + " " + chance.getAttributeName() + " per level.\n" +
                format.percentWithSignAndUnit(damage.bonusPerLevel) + " " + damage.getAttributeName() + " per level.";
    }
}
