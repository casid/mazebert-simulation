package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.potions.Potion;

public strictfp class TinkerAuraEffect extends Ability<Tower> implements OnPotionConsumedListener {

    public static final float BONUS = 0.05f;
    public static final float[] BONUS_PER_POTION = {
            0.001f, // Common
            0.002f, // Uncommon
            0.004f, // Rare
            0.04f, // Unique
            0.05f, // Legendary
    };

    private final Tinker tinker;
    private float currentBonus;

    public TinkerAuraEffect(Tinker tinker, TinkerAura aura) {
        this.tinker = tinker;
        setOrigin(aura);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        tinker.onPotionConsumed.add(this);
        addBonus();
    }

    @Override
    protected void dispose(Tower unit) {
        removeBonus();
        tinker.onPotionConsumed.remove(this);
        super.dispose(unit);
    }

    private void addBonus() {
        if (currentBonus == 0) {
            currentBonus = calculateCurrentBonus();
            addToAttribute(currentBonus);
        }
    }

    private void removeBonus() {
        if (currentBonus > 0) {
            addToAttribute(-currentBonus);
            currentBonus = 0;
        }
    }

    private float calculateCurrentBonus() {
        return getOriginAura().calculateCurrentBonus();
    }

    private void addToAttribute(float currentBonus) {
        getUnit().addLuck(currentBonus);
    }

    private TinkerAura getOriginAura() {
        return (TinkerAura) getOrigin();
    }

    @Override
    public void onPotionConsumed(Tower tower, Potion potion) {
        removeBonus();
        addBonus();
    }
}
