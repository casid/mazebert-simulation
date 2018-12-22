package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnGenderChangedListener;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class HuliMojo extends AuraAbility<Tower, Tower> implements OnLevelChangedListener, OnGenderChangedListener {

    private static final float CRIT_CHANCE_PER_TOWER = 0.04f;
    private static final float CRIT_CHANCE_PER_LEVEL = 0.0003f;

    private float critBonus;
    private int otherGenders;

    public HuliMojo() {
        super(Tower.class);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onLevelChanged.add(this);
        unit.onGenderChanged.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onLevelChanged.remove(this);
        unit.onGenderChanged.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        updateBonus();
    }

    public void updateBonus() {
        getUnit().addCritChance(-critBonus);
        critBonus = getOtherGendersInAura() * (CRIT_CHANCE_PER_TOWER + getUnit().getLevel() * CRIT_CHANCE_PER_LEVEL);
        getUnit().addCritChance(critBonus);
    }

    private int getOtherGendersInAura() {
        otherGenders = 0;
        forEach(this::countOtherGender);
        return otherGenders;
    }

    private void countOtherGender(Tower tower) {
        if (getUnit().getGender() == Gender.Male && tower.getGender() == Gender.Female) {
            ++otherGenders;
        }
        if (getUnit().getGender() == Gender.Female && tower.getGender() == Gender.Male) {
            ++otherGenders;
        }
    }

    @Override
    protected boolean isQualifiedForAura(Tower unit) {
        return unit != getUnit();
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        updateBonus();
        unit.onGenderChanged.add(this);
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        updateBonus();
        unit.onGenderChanged.remove(this);
    }

    @Override
    public void onGenderChanged(Unit unit) {
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
        String otherGender = getUnit().getGender() == Gender.Male ? "female" : "male";
        return "Hulis crit chance is increased by " + format.percent(CRIT_CHANCE_PER_TOWER) + "% for each " + otherGender + " tower in range.";
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
