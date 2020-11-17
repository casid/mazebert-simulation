package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnRangeChangedListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class EldritchMarshNecklaceAbility extends StackableAbility<Tower> implements OnRangeChangedListener {

    public static final float itemQualityBonus = 0.05f;
    public static final float itemChanceBonus = -0.4f;

    private float itemQuality;
    private float itemChance;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onRangeChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onRangeChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        getUnit().addItemQuality(-itemQuality);
        getUnit().addItemChance(-itemChance);

        float multiplier = getMultiplier();
        itemQuality = multiplier * itemQualityBonus * getWaterTiles();
        itemChance = multiplier * itemChanceBonus;

        getUnit().addItemQuality(itemQuality);
        getUnit().addItemChance(itemChance);
    }

    private int getWaterTiles() {
        return Sim.context().gameGateway.getMap().countWaterTiles(getUnit().getX(), getUnit().getY(), getUnit().getRange());
    }

    @Override
    public void onRangeChanged(Tower tower) {
        updateStacks();
    }

    private float getMultiplier() {
        return getStackCount() * getUnit().getEldritchCardModifier();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(itemQualityBonus) + " item quality per water tile in range.\n" +
                format.percentWithSignAndUnit(itemChanceBonus) + " item chance.";
    }
}
