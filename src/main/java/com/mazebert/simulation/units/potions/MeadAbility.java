package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Viking;

public strictfp class MeadAbility extends StackableAbility<Tower> {
    private static final float damageBonus = 0.14f;
    private static final float critChanceBonus = 0.02f;
    private static final float critDamageBonus = 0.2f;
    private static final float attackMalus = 0.01f;

    private int addedStacks;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        addEffect();
    }

    @Override
    protected void dispose(Tower unit) {
        removeEffect();
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        removeEffect();
        addEffect();
    }

    private void addEffect() {
        if (isVikingTower()) {
            applyEffect(getStackCount());
            addedStacks = getStackCount();
        }
    }

    private void removeEffect() {
        if (addedStacks > 0) {
            applyEffect(-addedStacks);
            addedStacks = 0;
        }
    }

    private void applyEffect(int stacks) {
        getUnit().addAddedRelativeBaseDamage(stacks * damageBonus);
        getUnit().addCritChance(stacks * critChanceBonus);
        getUnit().addCritDamage(stacks * critDamageBonus);
        getUnit().addChanceToMiss(stacks * attackMalus);
    }

    private boolean isVikingTower() {
        return getUnit() instanceof Viking;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Rage of the Viking!";
    }

    @Override
    public String getDescription() {
        return "Mead let's every Viking enrage! Be careful not to overdo it, though. Has no effect on other towers than Vikings.";
    }

    @Override
    public String getLevelBonus() {
        return "+ " + format.percent(damageBonus) + "% damage.\n" +
                "+ " + format.percent(critChanceBonus) + "% crit chance.\n" +
                "+ " + format.percent(critDamageBonus) + "% crit damage.\n" +
                "+ " + format.percent(attackMalus) + "% chance to miss the target.";
    }
}
