package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.CooldownUnitAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class HuliEat extends CooldownUnitAbility<Tower> implements OnAttackListener {

    private static final float DAMAGE_PER_BANANA = 0.02f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private boolean canEatBanana;
    private boolean enabled = true;
    private int bananasEaten;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    public void dispose(Tower unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    protected boolean onCooldownReached() {
        if (canEatBanana && enabled) {
            eatBanana();
        } else {
            canEatBanana = true;
        }
        return true;
    }

    private void eatBanana() {
        int maxBananas = 50 + getUnit().getLevel();
        if (bananasEaten < maxBananas) {
            ++bananasEaten;

            getUnit().addAddedRelativeBaseDamage(DAMAGE_PER_BANANA);

            if (simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Banana eaten!", 0xffff00);
            }
        }
    }

    @Override
    public void onAttack(Creep target) {
        canEatBanana = false; // Tower attacked, can't eat the next banana
    }

    public int getBananasEaten() {
        return bananasEaten;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "More for me!";
    }

    @Override
    public String getDescription() {
        if (getUnit().getGender() == Gender.Male) {
            return "Whenever Huli eats a banana, his damage increases by 2%. Huli can eat 50 bananas.";
        } else {
            return "Whenever Huli eats a banana, her damage increases by 2%. Huli can eat 50 bananas.";
        }
    }

    @Override
    public String getLevelBonus() {
        return "+1 banana per level.";
    }

    @Override
    public String getIconFile() {
        return "0024_stash_512";
    }
}
