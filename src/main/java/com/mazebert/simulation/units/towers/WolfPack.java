package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.systems.WolfSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class WolfPack extends Ability<Tower> implements OnKillListener {

    public static final int XP = 1;

    private final WolfSystem wolfSystem = Sim.context().wolfSystem;

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
        wolfSystem.giveExperienceToOtherWolfTowers((Wolf)getUnit(), XP);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Wolf pack";
    }

    @Override
    public String getDescription() {
        return "Everytime a wolf kills, all other wolves gain +" + XP + " experience.";
    }

    @Override
    public String getIconFile() {
        return "0076_wolf_howl_512";
    }
}
