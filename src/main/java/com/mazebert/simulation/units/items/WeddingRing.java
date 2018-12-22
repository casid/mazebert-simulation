package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp abstract class WeddingRing extends Item {

    public WeddingRing(int index) {
        super(new WeddingRingAbility(index));
    }

    @Override
    public String getName() {
        return "Wedding Ring";
    }

    @Override
    public String getDescription() {
        return "Bound for eternity.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getIcon() {
        return "wedding_ring_512";
    }

    @Override
    public int getItemLevel() {
        return 71;
    }

    @Override
    public String getAuthor() {
        return "korn7809";
    }
}
