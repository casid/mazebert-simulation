package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.TutorialPotion;

public strictfp class TinkerAura extends AuraAbility<Tinker, Tower> implements OnPotionConsumedListener {

    private final float bonus;
    private final float[] bonusPerPotion = new float[5];

    private int[] potionsByRarity = new int[Rarity.VALUES.length];

    public TinkerAura() {
        super(CardCategory.Tower, Tower.class, 2);

        if (Sim.context().version >= Sim.vDoLEndBeta3) {
            bonus = 0.1f;
            bonusPerPotion[0] = 0.002f; // Common
            bonusPerPotion[1] = 0.004f; // Uncommon
            bonusPerPotion[2] = 0.008f; // Rare
            bonusPerPotion[3] = 0.06f; // Unique
            bonusPerPotion[4] = 0.08f; // Legendary
        } else {
            bonus = 0.05f;
            bonusPerPotion[0] = 0.001f; // Common
            bonusPerPotion[1] = 0.002f; // Uncommon
            bonusPerPotion[2] = 0.004f; // Rare
            bonusPerPotion[3] = 0.04f; // Unique
            bonusPerPotion[4] = 0.05f; // Legendary
        }
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
        unit.removeAbility(TinkerAuraEffect.class, this);
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
        return "The luck of allies within range is increased by " + format.percentWithSignAndUnit(bonus) + ". Luck increases whenever the Tinker drinks a potion (cosmetic potions do not count).";
    }

    @Override
    public String getIconFile() {
        return "18_goatskin_drinking_bottle_512";
    }

    @Override
    public String getLevelBonus() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Rarity.VALUES.length; ++i) {
            result.append(format.percentWithSignAndUnit(bonusPerPotion[i])).append(" per ").append(format.rarity(Rarity.VALUES[i], true)).append(" potion.");
            if (i < Rarity.VALUES.length - 1) {
                result.append('\n');
            }
        }

        return result.toString();
    }

    @Override
    public void onPotionConsumed(Tower tower, Potion potion) {
        if (isSupported(potion)) {
            ++potionsByRarity[potion.getRarity().ordinal()];
        }
    }

    private boolean isSupported(Potion potion) {
        if (potion.isSupporterReward()) {
            return false;
        }

        if (potion instanceof TutorialPotion) {
            return false;
        }

        return true;
    }

    public float calculateCurrentBonus() {
        float currentBonus = bonus;
        for (int i = 0; i < Rarity.VALUES.length; ++i) {
            currentBonus += potionsByRarity[i] * bonusPerPotion[i];
        }
        return currentBonus;
    }
}
