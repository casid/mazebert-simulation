package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.ActiveAbility;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class PhoenixRebirth extends ActiveAbility implements OnUpdateListener {

    public static final long GOLD_COST = 1000;
    public static final int DAMAGE_GAIN = 7;
    public static final int REBIRTH_TIME = 10;

    private float rebirthTime;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUpdate.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUpdate.remove(this);
        super.dispose(unit);
    }

    @Override
    public float getReadyProgress() {
        Wizard wizard = getUnit().getWizard();
        if (wizard.gold < GOLD_COST) {
            return 0;
        }
        if (wizard.towerStash.getIndex(TowerType.Phoenix) == -1) {
            return 0;
        }

        return 1;
    }

    @Override
    public void activate() {
        Wizard wizard = getUnit().getWizard();
        wizard.addGold(-GOLD_COST);
        wizard.towerStash.remove(TowerType.Phoenix);

        getUnit().addAddedAbsoluteBaseDamage(7);
        rebirthTime += REBIRTH_TIME;
    }

    @Override
    public void onUpdate(float dt) {
        if (rebirthTime > 0) {
            rebirthTime -= dt;
            if (rebirthTime < 0) {
                rebirthTime = 0;
            }
        }
    }

    public boolean isAlive() {
        return rebirthTime == 0;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Rebirth";
    }

    @Override
    public String getDescription() {
        return "Sacrifice " + format.gold(GOLD_COST, getCurrency()) + " and a " + format.card(TowerType.Phoenix) + " card in your hand to be reborn from ash and permanently gain +" + DAMAGE_GAIN + " base damage.";
    }

    @Override
    public String getIconFile() {
        return "cup_512"; // TODO
    }

    @Override
    public String getLevelBonus() {
        return "Deal no damage for " + format.seconds(REBIRTH_TIME) + "s";
    }
}
