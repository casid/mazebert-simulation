package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class BookwormBooksAbility extends Ability<Hero> implements OnUnitAddedListener {

    @Override
    protected void initialize(Hero unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Hero unit) {
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        unit.onUnitAdded.remove(this);

        Wizard wizard = unit.getWizard();
        wizard.itemStash.add(ItemType.SchoolBook);
        wizard.itemStash.add(ItemType.FrozenBook);
        if (wizard.ownsFoilCard(ItemType.Necronomicon)) {
            wizard.itemStash.add(ItemType.Necronomicon);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Take These";
    }

    @Override
    public String getDescription() {
        return "A copy of every book item is added\nto your starting hand.";
    }
}
