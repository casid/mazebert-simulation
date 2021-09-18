package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Thor;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class Mjoelnir extends Item {

    private static Ability[] getAbilities() {
        if (Sim.context().version >= Sim.vDoL) {
            return new Ability[]{new MjoelnirChainAbility(), new MjoelnirCullingStrikeAbility()};
        } else {
            return new Ability[]{new MjoelnirChainAbility()};
        }
    }

    private final Element originalElement;
    private Element element;

    private final String originalDescription;
    private String description;

    public Mjoelnir() {
        super(getAbilities());

        originalElement = Element.Unknown;
        element = originalElement;

        originalDescription = "Thor's mighty hammer.";
        description = originalDescription;
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, false, 2021, "Chain lightning triggers every 2nd creep (from 3rd).", "Chain lightning deals 16% damage (from 24%)."),
                new ChangelogEntry(Sim.vDoL, false, 2019, "Instantly kills damaged creeps left behind with less than 10% health."),
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public void onEquipped(Tower tower) {
        if (tower instanceof Thor) {
            element = Element.Light;
            description = "My precious hammer.";
        }
    }

    @Override
    public void onDropped(Tower tower) {
        if (tower instanceof Thor) {
            element = originalElement;
            description = originalDescription;
        }
    }

    @Override
    public String getName() {
        return "Mjoelnir";
    }

    @Override
    public String getDescription() {
        return description;
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
        return Sim.context().version < Sim.vRoC;
    }

    @Override
    public Element getElement() {
        return element;
    }
}
