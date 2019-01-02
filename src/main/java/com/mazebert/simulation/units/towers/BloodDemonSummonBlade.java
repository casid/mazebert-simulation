package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.BloodDemonBlade;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;

public strictfp class BloodDemonSummonBlade extends Ability<Tower> implements OnUnitAddedListener {

    private static final float healthReduction = 0.01f;
    private static final int bladeDamagePerLifeLost = 1;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        dropPreviousItem();
        createBlade();
    }

    private void createBlade() {
        float healthToLose = getUnit().getWizard().health - healthReduction;
        getUnit().getWizard().addHealth(-healthToLose);

        BloodDemonBlade blade = new BloodDemonBlade();
        blade.setDamage(100.0f * bladeDamagePerLifeLost * healthToLose);
        getUnit().getWizard().itemStash.setUnique(ItemType.BloodDemonBlade, blade);

        getUnit().setItem(0, blade);
    }

    private void dropPreviousItem() {
        Item previousItem = getUnit().getItem(0);
        if (previousItem != null) {
            getUnit().getWizard().itemStash.add(previousItem.getType());
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Bathe in your blood";
    }

    @Override
    public String getDescription() {
        return "When Blood Demon is built, your health is reduced to " + format.percent(healthReduction) + "%. In exchange, a legendary blade is summoned and put into Blood Demon's inventory.";
    }

    @Override
    public String getLevelBonus() {
        return "+ " + bladeDamagePerLifeLost + " base damage on blade / life lost";
    }

    @Override
    public String getIconFile() {
        return "blood_demon_blade_512";
    }
}
