package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnMissListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.abilities.LuckWithLevelBonusAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class PaintingOfSoleaAbility extends LuckWithLevelBonusAbility implements OnMissListener {

    private static final float luckBonus = 0.05f;
    private static final float attackSpeedBonus = 0.1f;
    private static final float missChance = 0.2f;
    private static final float xpBonus = 1.0f;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    public PaintingOfSoleaAbility() {
        super(luckBonus, 0.0f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addAttackSpeed(attackSpeedBonus);
        unit.addChanceToMiss(missChance);
        unit.onMiss.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onMiss.remove(this);
        unit.addAttackSpeed(-attackSpeedBonus);
        unit.addChanceToMiss(-missChance);
        super.dispose(unit);
    }

    @Override
    public void onMiss(Object origin, Creep target) {
        experienceSystem.grantExperience(getUnit(), xpBonus);
    }

    @Override
    public String getTitle() {
        return "Beautiful distraction";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(attackSpeedBonus) + " attack speed\n" +
                format.percentWithSignAndUnit(luckBonus) + " luck\n" +
                format.percent(missChance) + " % chance, that the tower looks at the picture and misses his attack. Enlighted by the beauty, the tower gets " + format.experience(xpBonus) + " experience.";
    }
}
