package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Yggdrasil;

public strictfp class BranchOfYggdrasil extends Item {

    public BranchOfYggdrasil() {
        super(new BranchOfYggdrasilAbility());
    }

    @Override
    public String getName() {
        return "Branch of Yggdrasil";
    }

    @Override
    public String getDescription() {
        return "This branch reaches all over the world, back to the Great Tree of Life.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.5";
    }

    @Override
    public String getIcon() {
        return "branch_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isUniqueDrop() {
        return false;
    }

    @Override
    public boolean isForbiddenToEquip(Tower tower) {
        if (tower instanceof Yggdrasil) {
            return false;
        }
        if (tower.getElement() != Element.Nature) {
            return true;
        }
        return tower.getItemCount(getType()) > 0;
    }
}
