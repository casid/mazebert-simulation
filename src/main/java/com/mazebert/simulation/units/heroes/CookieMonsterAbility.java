package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Context;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Currency;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class CookieMonsterAbility extends Ability<Hero> implements OnUnitAddedListener {

    @Override
    protected void initialize(Hero unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        Context.currency = Currency.Cookie;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Cookie Coins";
    }

    @Override
    public String getDescription() {
        return 	"Creeps drop cookies instead of gold.";
    }
}
