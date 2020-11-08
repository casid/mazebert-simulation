package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CthulhuAbility extends HeroTowerBuffAbility implements OnPotionConsumedListener {

    private static final int range = 1;
    private static final float potionEffectivenessMalus = -0.6f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addPotionEffectiveness(potionEffectivenessMalus);
        tower.onPotionConsumed.add(this);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The Great Dreamer";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(potionEffectivenessMalus) + " effect of consumed potions.\n" +
                "Potions splash to towers in " + range + " range.";
    }

    @Override
    public void onPotionConsumed(Tower tower, Potion potion) {
        Sim.context().unitGateway.forEachInRange(tower.getX(), tower.getY(), range, Tower.class, towerInRange -> {
            if (towerInRange != tower && towerInRange.getPlayerId() == tower.getPlayerId()) {
                potion.applyTo(towerInRange);
            }
        });
    }
}
