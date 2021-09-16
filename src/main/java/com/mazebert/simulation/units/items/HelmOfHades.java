package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.towers.Hel;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class HelmOfHades extends Item {

    private static final String originalName = "Helm of Hades";
    private static final String originalDescription = "This helmet was once given to Hades, ruler of the underworld.";

    private String name = originalName;
    private String description = originalDescription;
    private boolean dark;

    public HelmOfHades() {
        setAbilities(new HelmOfHadesRangeAbility(), new HelmOfHadesInvisibleAbility(this));
    }

    public void changeName(Tower carrier) {
        if (carrier instanceof Hel) {
            name = "Helm of Helheim";
            description = originalDescription + " But " + format.card(TowerType.Hel) + " rules now.";
            this.dark = true;
        }
    }

    public void resetName() {
        name = originalName;
        description = originalDescription;
        dark = false;
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }

    @Override
    public String getIcon() {
        return "0039_helmet_512";
    }

    @Override
    public int getItemLevel() {
        return 77;
    }

    @Override
    public boolean isDark() {
        return dark;
    }
}
