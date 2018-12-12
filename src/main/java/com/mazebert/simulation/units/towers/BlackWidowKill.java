package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public class BlackWidowKill extends Ability<Tower> implements OnKillListener {
    private static final int XP = 4;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

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
        experienceSystem.grantExperience(getUnit(), XP);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Personal satisfaction";
    }

    @Override
    public String getDescription() {
        return "When Black Widow kills a seduced creep on her own she gains " + XP + " experience.";
    }

    @Override
    public String getIconFile() {
        return "0013_flowers_512";
    }
}
