package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.listeners.OnPotionEffectivenessChangedListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class LeuchtFeuerAbility extends StackableAbility<Tower> implements OnPotionEffectivenessChangedListener {
    private int maxLevels;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        unit.onPotionEffectivenessChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onPotionEffectivenessChanged.remove(this);

        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        removeStacks();
        addStacks();
    }

    private void removeStacks() {
        getUnit().addMaxLevel(-maxLevels);
        maxLevels = 0;
    }

    private void addStacks() {
        // TODO ensure that Beacon of Hope is still on the map
        if (getUnit().getElement() == Element.Light) {
            maxLevels = StrictMath.round(getStackCount() * getUnit().getPotionEffectiveness());
            getUnit().addMaxLevel(maxLevels);
        }
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public void onPotionEffectivenessChanged(Tower tower) {
        updateStacks();
    }
}
