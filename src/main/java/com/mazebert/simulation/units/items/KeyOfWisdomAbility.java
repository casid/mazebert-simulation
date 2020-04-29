package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.abilities.CooldownAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class KeyOfWisdomAbility extends CooldownAbility<Tower> {
    public static final float cooldown = 7.0f;
    public static final int range = 3;
    public static final float experience = 4.0f;
    public static final float experienceBonus = 0.02f;

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ExperienceSystem experienceSystem = Sim.context().experienceSystem;

    @Override
    protected float getCooldown() {
        return cooldown;
    }

    @Override
    protected boolean onCooldownReached() {
        Tower tower = unitGateway.findRandomTowerInRange(getUnit().getX(), getUnit().getY(), range);
        if (tower != null) {
            experienceSystem.grantExperience(tower, experience + getUnit().getLevel() * experienceBonus);
        }
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Enlightenment";
    }

    @Override
    public String getLevelBonus() {
        return "Every " + format.cooldown(cooldown) + ", a random tower in " + range + " range gains " + format.experience(experience) + " experience.\n+" + format.experience(experienceBonus) + " experience per level.";
    }
}
