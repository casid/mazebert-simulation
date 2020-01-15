package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnPotionConsumedListener;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Yggdrasil;

public strictfp class BranchOfYggdrasilAbility extends Ability<Tower> implements OnPotionConsumedListener {

    private final Element element;
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    public BranchOfYggdrasilAbility(Element element) {
        this.element = element;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        Yggdrasil yggdrasil = getYggdrasil(unit);
        if (yggdrasil != null) {
            yggdrasil.onPotionConsumed.add(this);
        }
    }

    @Override
    protected void dispose(Tower unit) {
        Yggdrasil yggdrasil = getYggdrasil(unit);
        if (yggdrasil != null) {
            yggdrasil.onPotionConsumed.remove(this);
        }

        super.dispose(unit);
    }

    private Yggdrasil getYggdrasil(Tower unit) {
        return unitGateway.findUnit(Yggdrasil.class, unit.getPlayerId());
    }

    @Override
    public void onPotionConsumed(Tower yggdrasil, Potion potion) {
        if (getUnit().getElement() == element) {
            potion.applyTo(getUnit());
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Everything is connected";
    }

    @Override
    public String getDescription() {
        return "Whenever Yggdrasil drinks a potion, the carrier of this item receives the effect of that potion, too.";
    }

    @Override
    public String getLevelBonus() {
        return "Limited to 1\nLimited to " + format.element(element) + " towers";
    }
}
