package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;

public strictfp class BranchOfYggdrasilDarkness extends BranchOfYggdrasil {
    public BranchOfYggdrasilDarkness() {
        super(Element.Darkness);
    }

    @Override
    public boolean isDark() {
        return true;
    }
}
