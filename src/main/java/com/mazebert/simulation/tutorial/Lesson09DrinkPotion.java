package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.units.potions.CommonSpeed;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Dandelion;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson09DrinkPotion extends Lesson implements OnPotionConsumedListener {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    private Dandelion dandelion;

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        wizard.potionStash.add(PotionType.CommonSpeed);

        dandelion = unitGateway.findUnit(Dandelion.class, wizard.playerId);
        dandelion.onPotionConsumed.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        dandelion.onPotionConsumed.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onPotionConsumed(Tower tower, Potion potion) {
        if (potion instanceof CommonSpeed) {
            finish();
        }
    }
}
