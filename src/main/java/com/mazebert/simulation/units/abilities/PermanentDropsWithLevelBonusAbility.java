package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class PermanentDropsWithLevelBonusAbility extends StackableAbility<Tower> {
    private ItemChanceWithLevelBonusAbility chance;
    private ItemQualityWithLevelBonusAbility quality;

    public PermanentDropsWithLevelBonusAbility(float chanceBonus, float chanceBonusPerLevel, float qualityBonus, float qualityBonusPerLevel) {
        chance = new ItemChanceWithLevelBonusAbility(chanceBonus, chanceBonusPerLevel);
        quality = new ItemQualityWithLevelBonusAbility(qualityBonus, qualityBonusPerLevel);
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
        return "Come to me!";
    }

    @Override
    public String getDescription() {
        return "The item chance and item quality of the carrier are permanently increased by " + format.percent(chance.bonus) + "%.";
    }

    @Override
    public String getLevelBonus() {
        return chance.getLevelBonus() + "\n" + quality.getLevelBonus();
    }
}
