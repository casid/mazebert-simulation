package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.ActiveAbility;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class PhoenixRebirth extends ActiveAbility implements OnUpdateListener {

    public static final int REBIRTH_TIME = 10;

    private final long initialGoldCost = Sim.context().version >= Sim.vDoLEnd ? 500 : 1000;
    private final long goldCostPerRebirth = Sim.context().version >= Sim.vDoLEnd ? 500 : 0;
    private final int damageGain = Sim.context().version >= Sim.vDoLEnd ? 21 : 14;

    private long currentGoldCost = initialGoldCost;
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
        if (!isAlive()) {
            return 0;
        }

        Wizard wizard = getUnit().getWizard();
        if (wizard.gold < currentGoldCost) {
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
        wizard.addGold(-currentGoldCost);
        wizard.towerStash.remove(TowerType.Phoenix);

        getUnit().addAddedAbsoluteBaseDamage(damageGain);
        rebirthTime += REBIRTH_TIME;

        currentGoldCost += goldCostPerRebirth;
    }

    @Override
    public void onUpdate(float dt) {
        if (rebirthTime > 0) {
            rebirthTime -= dt;
            if (rebirthTime < 0) {
                rebirthTime = 0;
                if (!isDisposed()) {
                    onAbilityReady();
                }
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
        return "Sacrifice " + format.gold(currentGoldCost, getCurrency()) + " and a " + format.card(TowerType.Phoenix) + " card from your hand. After " + format.seconds(REBIRTH_TIME) + " Phoenix is reborn from ash and gains " + damageGain + " base damage.";
    }

    @Override
    public String getLevelBonus() {
        if (goldCostPerRebirth > 0) {
            return "+" + format.gold(goldCostPerRebirth, getCurrency()) + " cost per rebirth.";
        }
        return null;
    }

    @Override
    public String getIconFile() {
        return "powder_512";
    }
}
