package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.TowerType;

public class DeckMasterPower extends WizardPower {

    private TowerType selectedTower;

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);

        if (selectedTower == null) {
            return;
        }

        if (selectedTower.instance().getRarity().ordinal() > getRarity().ordinal()) {
            return;
        }

        unit.towerStash.add(selectedTower);
    }

    public void setSelectedTower(TowerType type) {
        this.selectedTower = type;
    }

    public TowerType getSelectedTower() {
        return selectedTower;
    }

    public Rarity getRarity() {
        int skillLevel = getSkillLevel();
        if (skillLevel <= 1) {
            return Rarity.Common;
        } else if (skillLevel <= 3) {
            return Rarity.Uncommon;
        } else if (skillLevel <= 6) {
            return Rarity.Rare;
        } else {
            return Rarity.Unique;
        }
    }

    @Override
    public int getRequiredLevel() {
        return 70;
    }

    @Override
    public int getMaxSkillLevel() {
        return 7;
    }

    @Override
    public String getTitle() {
        return "Deck Master";
    }

    @Override
    public String getDescription() {
        return "+ 1 card of your choice\n1 " + format.rarity(getRarity()) + " tower card";
    }

    @Override
    public String getIconFile() {
        return "0040_holyman_512";
    }
}
