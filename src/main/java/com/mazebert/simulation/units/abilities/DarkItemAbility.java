package com.mazebert.simulation.units.abilities;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnExperienceChangedListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.DarkForge;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DarkItemAbility extends StackableAbility<Tower> implements OnExperienceChangedListener, Consumer<DarkForge> {

    public static final float TRIBUTE = 0.15f;

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    private float tribute;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        if (!(unit instanceof DarkForge)) {
            unit.onExperienceChanged.add(this);
        }
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onExperienceChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onExperienceChanged(Unit unit, float oldExperience, float newExperience) {
        if (newExperience > oldExperience) {
            tribute = getStackCount() * TRIBUTE * (newExperience - oldExperience);
            experienceSystem.grantExperience(getUnit(), -tribute);

            unitGateway.forEachTower(DarkForge.class, this);
        }
    }

    @Override
    public void accept(DarkForge darkForge) {
        experienceSystem.grantExperience(darkForge, tribute);
    }

    @Override
    protected void updateStacks() {
        // nothing to do
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "<c=#a800ff>Dark Forge Tribute</c>";
    }

    @Override
    public String getDescription() {
        return "<c=#8800aa>Each time the carrier gains experience, it sacrifices " + format.percent(TRIBUTE) + "% of it in tribute to the forge.</c>";
    }
}
