package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.WolfSystem;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class WolfskinAbility extends StackableAbility<Tower> {

    public static final float damageBonus = 0.2f;
    public static final float critChanceBonus = 0.05f;
    public static final float critDamageBonus = 0.2f;

    private final WolfSystem wolfSystem = Sim.context().wolfSystem;

    private float damage;
    private float critChance;
    private float critDamage;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        wolfSystem.addPretender(unit);
    }

    @Override
    protected void dispose(Tower unit) {
        wolfSystem.removePretender(unit);
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        getUnit().addAddedRelativeBaseDamage(-damage);
        getUnit().addCritChance(-critChance);
        getUnit().addCritDamage(-critDamage);

        damage = getStackCount() * damageBonus;
        critChance = getStackCount() * critChanceBonus;
        critDamage = getStackCount() * critDamageBonus;

        getUnit().addAddedRelativeBaseDamage(damage);
        getUnit().addCritChance(critChance);
        getUnit().addCritDamage(critDamage);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "One with the pack";
    }

    @Override
    public String getDescription() {
        return "This tower's level counts as though the tower was a wolf, adding to total pack level.";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(damageBonus) + " damage\n" +
                format.percentWithSignAndUnit(critChanceBonus) + " crit chance\n" +
                format.percentWithSignAndUnit(critDamageBonus) + " crit damage";
    }
}
