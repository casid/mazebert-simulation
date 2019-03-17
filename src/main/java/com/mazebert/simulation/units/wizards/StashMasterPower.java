package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class StashMasterPower extends WizardTowerBuffPower {

    @Override
    protected void buffTower(Tower tower) {
        if (getSkillLevel() <= 0) {
            return;
        }
        if (tower.getRarity().ordinal() > getRarity().ordinal()) {
            return;
        }

        tower.addInventorySize(1);
    }

    public Rarity getRarity() {
        int skillLevel = getSkillLevel();
        if (skillLevel <= 2) {
            return Rarity.Common;
        } else if (skillLevel <= 4) {
            return Rarity.Uncommon;
        } else if (skillLevel <= 6) {
            return Rarity.Rare;
        } else if (skillLevel <= 9) {
            return Rarity.Unique;
        }
        return Rarity.Legendary;
    }

    @Override
    public int getRequiredLevel() {
        return 50;
    }

    @Override
    public String getTitle() {
        return "Stash Master";
    }

    @Override
    public String getDescription() {
        String suffix = ".";
        if (getRarity() != Rarity.Common) {
            suffix = " and below.";
        }
        return "+ 1 inventory slot\n" + format.rarity(getRarity(), false) + " towers" + suffix;
    }

    @Override
    public String getIconFile() {
        return "0040_holyman_512";
    }
}
