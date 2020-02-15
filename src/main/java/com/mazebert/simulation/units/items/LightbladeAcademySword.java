package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class LightbladeAcademySword extends Item {

    private final LightbladeAcademySwordAbility ability;

    public LightbladeAcademySword() {
        super(new LightbladeAcademySwordAbility(), new LightbladeAcademySetAbility());
        ability = getAbility(LightbladeAcademySwordAbility.class);
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getName() {
        return "Plasma Blade";
    }

    @Override
    public String getDescription() {
        return "An elegant weapon if you know what you're doing.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getIcon() {
        return ability.getBladeIcon();
    }

    @Override
    public int getItemLevel() {
        return 84;
    }
}
