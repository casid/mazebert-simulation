package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.listeners.OnRangeChangedListener;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AuraAbility;

public class HuliMojo extends AuraAbility<Tower, Tower> implements OnLevelChangedListener, OnRangeChangedListener {

    private static final float CRIT_CHANCE_PER_TOWER = 0.04f;
    private static final float CRIT_CHANCE_PER_LEVEL = 0.0003f;

    private float critBonus;

    public HuliMojo() {
        super(Tower.class, 3);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onLevelChanged.add(this);
        unit.onRangeChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        unit.onRangeChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onLevelChanged(Tower tower, int oldLevel, int newLevel) {
        updateBonus();
    }

    @Override
    public void onRangeChanged(Tower tower) {
        setRange(tower.getRange());
    }

    public void updateBonus() {
        getUnit().addCritChance(-critBonus);
        critBonus = getActiveSize() * (CRIT_CHANCE_PER_TOWER + getUnit().getLevel() * CRIT_CHANCE_PER_LEVEL);
        getUnit().addCritChance(critBonus);
    }

    @Override
    protected boolean isQualifiedForAura(Tower unit) {
        return unit.getGender() == Gender.Female;
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        updateBonus();
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        updateBonus();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Mojo";
    }

    @Override
    public String getDescription() {
        return "Hulis crit chance is increased by " + format.percent(CRIT_CHANCE_PER_TOWER) + "% for each female tower in range.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + format.percent(CRIT_CHANCE_PER_LEVEL) + "% crit chance per level.";
    }

    @Override
    public String getIconFile() {
        return "0092_Leather_Leg_Armor_512";
    }
}
