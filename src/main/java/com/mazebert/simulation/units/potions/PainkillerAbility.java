package com.mazebert.simulation.units.potions;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnUpdateListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public class PainkillerAbility extends Ability<Tower> implements OnUpdateListener, Consumer<Creep> {
    private float burndown = 3;
    private int nextBurndownTick = (int) burndown;

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
    public void onUpdate(float dt) {
        if (burndown <= nextBurndownTick) {
            if (nextBurndownTick > 0) {
                // TODO notification?
                // Mazebert.instance.hud.showTowerNotification(tower, _nextBurndownTick + "!", 0xff9900);
                --nextBurndownTick;
            } else {
                // TODO notification?
                // Mazebert.instance.hud.showTowerNotification(tower, "KA-BOOM!!!", 0xff9900);
                UnitGateway unitGateway = Sim.context().unitGateway;
                unitGateway.forEachInRange(getUnit().getX(), getUnit().getY(), getUnit().getLevel(), Creep.class, this);
                unitGateway.destroyTower(getUnit());
            }
        }

        burndown -= dt;
    }

    @Override
    public void accept(Creep creep) {
        getUnit().kill(creep);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Self destruction";
    }

    @Override
    public String getDescription() {
        return "When your tower drinks this potion, it has " + burndown + " more seconds to live. Then, your tower will explode in a big blast. Every creep in explosion range will die.";
    }

    @Override
    public String getLevelBonus() {
        return "+ 1 explosion range per tower level.";
    }
}
