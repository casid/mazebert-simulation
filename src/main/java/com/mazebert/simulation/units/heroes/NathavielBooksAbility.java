package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.ItemType;

public strictfp class NathavielBooksAbility extends Ability<Hero> implements OnUnitAddedListener {

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

        unit.getWizard().itemStash.add(ItemType.SchoolBook);
        unit.getWizard().itemStash.add(ItemType.FrozenBook);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Take these";
    }

    @Override
    public String getDescription() {
        return "A copy of every book item card is added to your starting hand.";
    }
}
