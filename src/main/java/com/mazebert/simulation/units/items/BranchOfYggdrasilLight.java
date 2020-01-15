package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;

public strictfp class BranchOfYggdrasilLight extends BranchOfYggdrasil {
    public BranchOfYggdrasilLight() {
        super(Element.Light);
    }

    @Override
    public boolean isLight() {
        return true;
    }
}
