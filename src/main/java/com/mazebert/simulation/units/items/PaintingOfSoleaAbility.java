package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnMissListener;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class PaintingOfSoleaAbility extends StackableAbility<Tower> implements OnMissListener {

    private static final float LUCK_BONUS = 0.05f;
    private static final float ATTACK_SPEED_BONUS = 0.1f;
    private static final float MISS_CHANCE = 0.2f;
    private static final float XP_BONUS = 1.0f;

    private float luckBonus;
    private float attackSpeedBonus;
    private float missChance;
    private float xpBonus;

    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    public PaintingOfSoleaAbility() {
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onMiss.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onMiss.remove(this);
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        Tower unit = getUnit();
        int stackCount = getStackCount();

        unit.addAttackSpeed(-attackSpeedBonus);
        unit.addChanceToMiss(-missChance);
        unit.addLuck(-luckBonus);

        attackSpeedBonus = stackCount * ATTACK_SPEED_BONUS;
        missChance = stackCount * MISS_CHANCE;
        luckBonus = stackCount * LUCK_BONUS;
        xpBonus = stackCount * XP_BONUS;

        unit.addAttackSpeed(attackSpeedBonus);
        unit.addChanceToMiss(missChance);
        unit.addLuck(luckBonus);
    }

    @Override
    public void onMiss(Object origin, Creep target) {
        experienceSystem.grantExperience(getUnit(), xpBonus);
    }

    @Override
    public String getTitle() {
        return "Beautiful Distraction";
    }

    @Override
    public String getDescription() {
        return format.percent(MISS_CHANCE) + " % chance that the carrier stares at Solea and misses its attack. Enlighted by Solea's beauty, the carrier gains " + format.experienceWithSignAndUnit(XP_BONUS) + ".";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(ATTACK_SPEED_BONUS) + " attack speed.\n" +
                format.percentWithSignAndUnit(LUCK_BONUS) + " luck.\n";
    }
}
