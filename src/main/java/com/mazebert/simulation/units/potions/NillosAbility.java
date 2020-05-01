package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.listeners.OnPotionEffectivenessChangedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class NillosAbility extends Ability<Tower> implements OnPotionEffectivenessChangedListener {
    private static final float luckAdd = 0.2f;
    private static final float attackSpeedAdd = 0.4f;
    private static final float itemChance = 0.2f;
    private static final float itemQuality = 0.2f;

    private float addedLuck;
    private float addedAttackSpeed;
    private float addedItemChance;
    private float addedItemQuality;

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

    private void addEffect() {
        addedLuck = luckAdd * getUnit().getPotionEffectiveness();
        addedAttackSpeed = attackSpeedAdd * getUnit().getPotionEffectiveness();
        addedItemChance = itemChance * getUnit().getPotionEffectiveness();
        addedItemQuality = itemQuality * getUnit().getPotionEffectiveness();

        getUnit().addLuck(addedLuck);
        getUnit().addAttackSpeed(addedAttackSpeed);
        getUnit().addItemChance(addedItemChance);
        getUnit().addItemQuality(addedItemQuality);
    }

    private void removeEffect() {
        getUnit().addLuck(-addedLuck);
        getUnit().addAttackSpeed(-addedAttackSpeed);
        getUnit().addItemChance(-addedItemChance);
        getUnit().addItemQuality(-addedItemQuality);
    }

    @Override
    public void onPotionEffectivenessChanged(Tower tower) {
        removeEffect();
        addEffect();
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Work Smarter, Not Harder";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(luckAdd) + " luck.\n" +
                format.percentWithSignAndUnit(attackSpeedAdd) + " attack speed.\n" +
                format.percentWithSignAndUnit(itemChance) + " item chance.\n" +
                format.percentWithSignAndUnit(itemQuality) + " item quality.";
    }
}
