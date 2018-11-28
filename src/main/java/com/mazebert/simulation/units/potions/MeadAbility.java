package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class MeadAbility extends StackableAbility<Tower> {
    private static final float damageBonus = 0.14f;
    private static final float critChanceBonus = 0.02f;
    private static final float critDamageBonus = 0.2f;
    private static final float attackMalus = 0.01f;

    private boolean effectAdded;

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
            applyEffect(+1);
            effectAdded = true;
        }
    }

    private void removeEffect() {
        if (effectAdded) {
            applyEffect(-1);
            effectAdded = false;
        }
    }

    private void applyEffect(int factor) {
        int stacks = factor * getStackCount();
        getUnit().addAddedRelativeBaseDamage(stacks * damageBonus);
        getUnit().addCritChance(stacks * critChanceBonus);
        getUnit().addCritDamage(stacks * critDamageBonus);
        getUnit().addChanceToMiss(stacks * attackMalus);
    }

    private boolean isVikingTower() {
        // TODO implement with Viking
        return false;
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
        return "+ " + formatPlugin.percent(damageBonus) + "% damage.\n" +
                "+ " + formatPlugin.percent(critChanceBonus) + "% crit chance.\n" +
                "+ " + formatPlugin.percent(critDamageBonus) + "% crit damage.\n" +
                "+ " + formatPlugin.percent(attackMalus) + "% chance to miss the target.";
    }
}
