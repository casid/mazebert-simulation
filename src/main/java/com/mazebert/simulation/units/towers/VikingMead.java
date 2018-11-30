package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnKillListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.potions.PotionType;

public class VikingMead extends Ability<Tower> implements OnKillListener {

    private static final float CHANCE = 0.01f;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onKill.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onKill.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onKill(Creep target) {
        if (getUnit().isAbilityTriggered(CHANCE * StrictMath.min(1, target.getDropChance()))) {
            getUnit().getWizard().potionStash.add(PotionType.Mead);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Drink for Victory";
    }

    @Override
    public String getDescription() {
        return "Whenever Holgar kills a creep, there is a " + format.percent(CHANCE) + "% chance he finds a bottle of mead.";
    }

    @Override
    public String getIconFile() {
        return "9005_MeadPotion";
    }
}
