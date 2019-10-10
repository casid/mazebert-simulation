package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.potions.Potion;

public strictfp class TinkerAura extends AuraAbility<Tinker, Tower> implements OnPotionConsumedListener {

    private int[] potionsByRarity = new int[Rarity.VALUES.length];

    public TinkerAura() {
        super(CardCategory.Tower, Tower.class, 1);
    }

    @Override
    protected void initialize(Tinker unit) {
        super.initialize(unit);
        unit.onPotionConsumed.add(this);
    }

    @Override
    protected void dispose(Tinker unit) {
        unit.onPotionConsumed.remove(this);
        super.dispose(unit);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        unit.addAbility(new TinkerAuraEffect(getUnit(), this));
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        TinkerAuraEffect ability = unit.getAbility(TinkerAuraEffect.class, this);
        if (ability != null) {
            unit.removeAbility(ability);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Lucky Potion";
    }

    @Override
    public String getDescription() {
        return "The luck of allies within range is increased by " + format.percentWithSignAndUnit(TinkerAuraEffect.BONUS) + ". Luck increases whenever the Tinker drinks a potion (cosmetic potions do not count).";
    }

    @Override
    public String getIconFile() {
        return "18_goatskin_drinking_bottle_512";
    }

    @Override
    public String getLevelBonus() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Rarity.VALUES.length; ++i) {
            result.append(format.percentWithSignAndUnit(TinkerAuraEffect.BONUS_PER_POTION[i])).append(" per ").append(format.rarity(Rarity.VALUES[i], true)).append(" potion.");
            if (i < Rarity.VALUES.length - 1) {
                result.append('\n');
            }
        }

        return result.toString();
    }

    @Override
    public void onPotionConsumed(Tower tower, Potion potion) {
        if (!potion.isSupporterReward()) {
            ++potionsByRarity[potion.getRarity().ordinal()];
        }
    }

    public float calculateCurrentBonus() {
        float currentBonus = TinkerAuraEffect.BONUS;
        for (int i = 0; i < Rarity.VALUES.length; ++i) {
            currentBonus += potionsByRarity[i] * TinkerAuraEffect.BONUS_PER_POTION[i];
        }
        return currentBonus;
    }
}
