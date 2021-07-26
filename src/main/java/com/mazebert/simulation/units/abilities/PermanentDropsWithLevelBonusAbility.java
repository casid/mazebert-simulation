package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class PermanentDropsWithLevelBonusAbility extends StackableAbility<Tower> {
    private final ItemChanceWithLevelBonusAbility chance;
    private final ItemQualityWithLevelBonusAbility quality;

    public PermanentDropsWithLevelBonusAbility(float chanceBonus, float chanceBonusPerLevel, float qualityBonus, float qualityBonusPerLevel) {
        if (Sim.context().version >= Sim.v26) {
            chance = new PermanentItemChanceWithLevelBonusAbility(chanceBonus, chanceBonusPerLevel);
            quality = new PermanentItemQualityWithLevelBonusAbility(qualityBonus, qualityBonusPerLevel);
        } else {
            chance = new ItemChanceWithLevelBonusAbility(chanceBonus, chanceBonusPerLevel);
            quality = new ItemQualityWithLevelBonusAbility(qualityBonus, qualityBonusPerLevel);
        }
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        chance.init(unit);
        quality.init(unit);
    }

    @Override
    protected void dispose(Tower unit) {
        chance.dispose(unit);
        quality.dispose(unit);
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        chance.setStackCount(getStackCount());
        quality.setStackCount(getStackCount());
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
        return "Tales of Treasure";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(chance.bonus) + " " + chance.getAttributeName() + ".\n" +
                format.percentWithSignAndUnit(quality.bonus) + " " + quality.getAttributeName() + ".\n" +
                format.percentWithSignAndUnit(chance.bonusPerLevel) + " " + chance.getAttributeName() + " per level.\n" +
                format.percentWithSignAndUnit(quality.bonusPerLevel) + " " + quality.getAttributeName() + " per level.";
    }
}
