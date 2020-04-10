package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.potions.Potion;

public strictfp class TinkerAuraEffect extends Ability<Tower> implements OnPotionConsumedListener {

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
