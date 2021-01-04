package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Yggdrasil;

public abstract strictfp class BranchOfYggdrasil extends Item {

    private final Element element;

    public BranchOfYggdrasil(Element element) {
        super(new BranchOfYggdrasilAbility(element));
        this.element = element;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public String getName() {
        return "Branch to " + element.norseWorld;
    }

    @Override
    public String getDescription() {
        return "This branch connects " + format.norseWorld(element) + " with the Great Tree of Life.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "2.1";
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
        return true;
    }

    @Override
    public boolean isForbiddenToEquip(Tower tower) {
        if (tower instanceof Yggdrasil) {
            return false;
        }
        return tower.getElement() != element;
    }

    @Override
    public boolean isTransferable() {
        return false;
    }
}
