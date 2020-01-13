package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.BranchOfYggdrasilLegacy;

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

        for (int i = 0; i < yggdrasil.getInventorySize(); ++i) {
            BranchOfYggdrasilLegacy item = new BranchOfYggdrasilLegacy();
            yggdrasil.setItem(i, item);
        }
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
        return "When summoned, Yggdrasils inventory is filled with its branches.";
    }

    @Override
    public String getIconFile() {
        return "branch_512";
    }
}
