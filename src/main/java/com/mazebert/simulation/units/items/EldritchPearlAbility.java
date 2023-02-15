package com.mazebert.simulation.units.items;

import com.mazebert.simulation.listeners.OnItemEquippedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchPearlAbility extends Ability<Tower> implements OnItemEquippedListener {

    public static final int multicritBonus = 1;
    public static final float luckBonus = 0.2f;

    private int multicrit;
    private float luck;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onItemEquipped.add(this);

        updateBonus();
    }

    @Override
    protected void dispose(Tower unit) {
        removeBonus();

        unit.onItemEquipped.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onItemEquipped(Tower tower, int index, Item oldItem, Item newItem, boolean userAction) {
        updateBonus();
    }

    private void updateBonus() {
        removeBonus();

        float multiplier = getMultiplier();
        multicrit = StrictMath.round(multiplier * multicritBonus * getUnit().getNumberOfEldritchItems());
        luck = multiplier * luckBonus;

        getUnit().addMulticrit(multicrit);
        getUnit().addLuck(luck);
    }

    private void removeBonus() {
        getUnit().addMulticrit(-multicrit);
        getUnit().addLuck(-luck);

        multicrit = 0;
        luck = 0.0f;
    }

    private float getMultiplier() {
        return getUnit().getEldritchCardModifier();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Cursed Tide";
    }

    @Override
    public String getLevelBonus() {
        return "+" + multicritBonus + " multicrit per eldritch item in inventory.\n" +
                format.percentWithSignAndUnit(luckBonus) + " luck.";
    }
}
