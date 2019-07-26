package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.listeners.OnPotionEffectivenessChangedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class TearsAbility extends Ability<Tower> implements OnPotionEffectivenessChangedListener {
    private static final float damage = 0.25f;
    private static final float critDamage = 0.5f;
    private static final float critChance = 0.05f;
    private static final int multicrit = 1;

    float addedDamage;
    float addedCritDamage;
    float addedCritChance;
    int addedMulticrit;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        addEffect();
        unit.onPotionEffectivenessChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        removeEffect();
        super.dispose(unit);
    }

    private void addEffect() {
        addedDamage = damage * getUnit().getPotionEffectiveness();
        addedCritChance = critChance * getUnit().getPotionEffectiveness();
        addedCritDamage = critDamage * getUnit().getPotionEffectiveness();
        addedMulticrit = StrictMath.round(multicrit * getUnit().getPotionEffectiveness());

        getUnit().addAddedRelativeBaseDamage(addedDamage);
        getUnit().addCritChance(addedCritChance);
        getUnit().addCritDamage(addedCritDamage);
        getUnit().addMulticrit(addedMulticrit);
    }

    private void removeEffect() {
        getUnit().addAddedRelativeBaseDamage(-addedDamage);
        getUnit().addCritChance(-addedCritChance);
        getUnit().addCritDamage(-addedCritDamage);
        getUnit().addMulticrit(-addedMulticrit);

        addedDamage = 0;
        addedCritDamage = 0;
        addedCritChance = 0;
        addedMulticrit = 0;
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
        return "Divine Intervention";
    }

    @Override
    public String getDescription() {
        return "+ " + format.percent(damage) + "% damage\n" +
                "+ " + format.percent(critDamage) + "% crit damage\n" +
                "+ " + format.percent(critChance) + "% crit chance\n" +
                "+ " + multicrit + " multicrit";
    }
}
