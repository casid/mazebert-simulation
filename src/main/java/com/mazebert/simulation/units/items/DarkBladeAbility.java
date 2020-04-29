package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DarkBladeAbility extends CooldownAbility<Tower> {

    public static final float experienceMalus = 1;
    public static final float critChanceBonus = 0.13f;
    public static final int multicritBonus = 3;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addCritChance(critChanceBonus);
        unit.addMulticrit(multicritBonus);
    }

    @Override
    public void dispose(Tower unit) {
        unit.addCritChance(-critChanceBonus);
        unit.addMulticrit(-multicritBonus);
        super.dispose(unit);
    }

    @Override
    protected float getCooldown() {
        return 7.0f;
    }

    @Override
    protected boolean onCooldownReached() {
        experienceSystem.grantExperience(getUnit(), -experienceMalus);
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Rising Darkness";
    }

    @Override
    public String getLevelBonus() {
        return "-" + format.experience(experienceMalus) + " experience every " + format.seconds(getCooldown()) + ".\n" + format.percentWithSignAndUnit(critChanceBonus) + " crit chance.\n+" + multicritBonus + " multicrit.";
    }
}
