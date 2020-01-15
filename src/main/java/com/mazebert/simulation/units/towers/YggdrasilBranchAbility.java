package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.*;

import java.util.Arrays;

public strictfp class YggdrasilBranchAbility extends Ability<Yggdrasil> implements OnUnitAddedListener {
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    protected void initialize(Yggdrasil unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Yggdrasil unit) {
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        Yggdrasil yggdrasil = getUnit();
        unitGateway.returnAllItemsToInventory(getUnit());

        yggdrasil.setItem(0, new BranchOfYggdrasilNature());
        yggdrasil.setItem(1, new BranchOfYggdrasilMetropolis());
        yggdrasil.setItem(2, new BranchOfYggdrasilDarkness());
        yggdrasil.setItem(3, new BranchOfYggdrasilLight());
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Branches spread far and wide";
    }

    @Override
    public String getDescription() {
        return format.listing("When summoned, Yggdrasils inventory contains branches to ", Arrays.asList(Element.getValues()), format::norseWorld, ".");
    }

    @Override
    public String getIconFile() {
        return "branch_512";
    }
}
