package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.quests.BeaconQuest;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class BeaconLevel extends Ability<Tower> implements OnLevelChangedListener {

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        unit.addMaxLevel(Balancing.MAX_TOWER_LEVEL_CAP - Balancing.MAX_TOWER_LEVEL);
        unit.onLevelChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        unit.addMaxLevel(Balancing.MAX_TOWER_LEVEL - Balancing.MAX_TOWER_LEVEL_CAP);

        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The Sky is the Limit";
    }

    @Override
    public String getDescription() {
        return "Has no level cap.\nWhenever Beacon gains a level above 99, it produces a " + format.card(PotionType.LeuchtFeuer) + " potion.";
    }

    @Override
    public String getIconFile() {
        return "leuchtfeuer_512";
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        Wizard wizard = getUnit().getWizard();

        for (int level = oldLevel + 1; level <= newLevel; ++level) {
            if (level > 99) {
                wizard.potionStash.add(PotionType.LeuchtFeuer);
            }

            if (level == BeaconQuest.requiredLevel) {
                wizard.addQuestProgress(BeaconQuest.class);
            }
        }
    }
}
