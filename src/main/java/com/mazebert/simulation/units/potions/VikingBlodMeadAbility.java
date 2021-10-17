package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class VikingBlodMeadAbility extends Ability<Tower> implements OnKillListener {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onKill.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onKill.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onKill(Creep target) {
        target.setExtraGore(true);
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
        return format.prophecyTitle("Bloodlust");
    }

    @Override
    public String getLevelBonus() {
        return format.prophecyDescription("Extra Gore");
    }
}
