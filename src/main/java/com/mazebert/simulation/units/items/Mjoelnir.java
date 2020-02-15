package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class Mjoelnir extends Item {

    private static Ability[] getAbilities() {
        if (Sim.context().version >= Sim.vDoL) {
            return new Ability[]{new MjoelnirChainAbility(), new MjoelnirCullingStrikeAbility()};
        } else {
            return new Ability[]{new MjoelnirChainAbility()};
        }
    }

    public Mjoelnir() {
        super(getAbilities());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getName() {
        return "Mjoelnir";
    }

    @Override
    public String getDescription() {
        return "Hammer of Thor.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getIcon() {
        return "mjoelnir-512";
    }

    @Override
    public int getItemLevel() {
        return 100;
    }

    @Override
    public String getAuthor() {
        return "Quofum";
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
