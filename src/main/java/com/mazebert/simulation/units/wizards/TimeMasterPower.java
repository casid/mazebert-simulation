package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;

public strictfp class TimeMasterPower extends WizardPower {

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        if (isInStartingHand()) {
            unit.itemStash.add(ItemType.ScepterOfTime);
        }
    }

    private boolean isInStartingHand() {
        return getSkillLevel() > 0;
    }

    @Override
    public int getRequiredLevel() {
        if (Sim.isDoLSeasonContent()) {
            return 1;
        }
        return 40;
    }

    @Override
    public int getMaxSkillLevel() {
        return 1;
    }

    @Override
    public String getTitle() {
        return "Time Master";
    }

    @Override
    public String getDescription() {
        Item scepter = ItemType.ScepterOfTime.instance();

        String description = format.colored(scepter.getName(), scepter.getRarity().color);
        if (isInStartingHand()) {
            description += " is in your\nstarting hand";
        } else {
            description += " can drop\nat round " + scepter.getItemLevel();
        }

        return description;
    }

    @Override
    public String getIconFile() {
        return "0000_poisondagger_512";
    }
}
