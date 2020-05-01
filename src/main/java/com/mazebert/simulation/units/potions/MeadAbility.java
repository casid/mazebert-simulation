package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.listeners.OnPotionEffectivenessChangedListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class MeadAbility extends StackableAbility<Tower> implements OnPotionEffectivenessChangedListener {
    public static final float damageBonus = 0.14f;
    public static final float critChanceBonus = 0.02f;
    public static final float critDamageBonus = 0.2f;
    public static final float attackMalus = 0.01f;

    private int addedStacks;
    private float addedDamageBonus;
    private float addedCritChanceBonus;
    private float addedCritDamageBonus;
    private float addedAttackMalus;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        addEffect();
        unit.onPotionEffectivenessChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onPotionEffectivenessChanged.remove(this);
        removeEffect();
        super.dispose(unit);
    }

    public void update() {
        updateStacks();
    }

    @Override
    protected void updateStacks() {
        removeEffect();
        addEffect();
    }

    private void addEffect() {
        if (getUnit().isViking()) {
            addedStacks = getStackCount();

            addedDamageBonus = addedStacks * damageBonus * getUnit().getPotionEffectiveness();
            addedCritChanceBonus = addedStacks * critChanceBonus * getUnit().getPotionEffectiveness();
            addedCritDamageBonus = addedStacks * critDamageBonus * getUnit().getPotionEffectiveness();
            addedAttackMalus = addedStacks * attackMalus * getUnit().getPotionEffectiveness();
            getUnit().addAddedRelativeBaseDamage(addedDamageBonus);
            getUnit().addCritChance(addedCritChanceBonus);
            getUnit().addCritDamage(addedCritDamageBonus);
            getUnit().addChanceToMiss(addedAttackMalus);
        }
    }

    private void removeEffect() {
        if (addedStacks > 0) {
            getUnit().addAddedRelativeBaseDamage(-addedDamageBonus);
            getUnit().addCritChance(-addedCritChanceBonus);
            getUnit().addCritDamage(-addedCritDamageBonus);
            getUnit().addChanceToMiss(-addedAttackMalus);
            addedDamageBonus = 0;
            addedCritChanceBonus = 0;
            addedCritDamageBonus = 0;
            addedAttackMalus = 0;

            addedStacks = 0;
        }
    }

    @Override
    public void onPotionEffectivenessChanged(Tower tower) {
        update();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Another!";
    }

    @Override
    public String getDescription() {
        return "Just be careful not to overdo it. When a Viking drinks this potion, he becomes enraged and gains:";
    }

    @Override
    public String getLevelBonus() {
        return "+ " + format.percent(damageBonus) + "% damage.\n" +
                "+ " + format.percent(critChanceBonus) + "% crit chance.\n" +
                "+ " + format.percent(critDamageBonus) + "% crit damage.\n" +
                "+ " + format.percent(attackMalus) + "% chance to miss the target.";
    }
}
