package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnAttackListener;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class PocketThiefGold extends Ability<Tower> implements OnAttackListener {

    private final LootSystem lootSystem = Sim.context().lootSystem;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onAttack.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onAttack.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAttack(Creep target) {
        if (getUnit().isAbilityTriggered(0.25f)) {
            lootSystem.grantGold(getUnit().getWizard(), getUnit(), getGold());
        }
    }

    private int getGold() {
        int level = getUnit().getLevel();
        if (level >= 12) {
            return 9;
        }
        if (level >= 6) {
            return 6;
        }
        return 3;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Pickpocketing";
    }

    @Override
    public String getDescription() {
        return "Each time Pocket Thief attacks, she has a 25% chance to steal 3 " + getCurrency().pluralLowercase + ".";
    }

    @Override
    public String getIconFile() {
        return "0061_money_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 3 " + getCurrency().pluralLowercase + " at level 6 and 12.";
    }
}
