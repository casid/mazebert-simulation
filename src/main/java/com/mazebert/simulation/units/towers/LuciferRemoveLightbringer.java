package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnItemEquippedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.Lightbringer;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.usecases.BuildTower;

public strictfp class LuciferRemoveLightbringer extends Ability<Tower> implements OnItemEquippedListener, OnUnitRemovedListener {

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onItemEquipped.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onItemEquipped.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onItemEquipped(Tower tower, int index, Item oldItem, Item newItem, boolean userAction) {
        if (oldItem instanceof Lightbringer) {
            if (userAction) {
                Wizard wizard = getUnit().getWizard();
                replaceWithFallenLucifer(wizard);
                destroyLightbringer(wizard);
            } else {
                tower.onUnitRemoved.add(this);
            }
        }
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        destroyLightbringer(unit.getWizard());
        unit.onUnitRemoved.remove(this);
    }

    private void replaceWithFallenLucifer(Wizard wizard) {
        LuciferFallen luciferFallen = new LuciferFallen();

        BuildTower buildTower = new BuildTower();
        wizard.towerStash.setUnique(TowerType.LuciferFallen, luciferFallen);
        buildTower.summonTower(luciferFallen, wizard, (int) getUnit().getX(), (int) getUnit().getY());
    }

    private void destroyLightbringer(Wizard wizard) {
        wizard.itemStash.remove(ItemType.Lightbringer);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Looming Darkness";
    }

    @Override
    public String getDescription() {
        return "When Lucifer gives Lightbringer away he turns into a fallen angel.";
    }

    @Override
    public String getIconFile() {
        return "blood_demon_blade_512"; // TODO
    }
}
