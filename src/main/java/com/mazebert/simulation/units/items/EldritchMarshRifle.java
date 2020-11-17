package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemAbility;

public strictfp class EldritchMarshRifle extends Item {
    public EldritchMarshRifle() {
        super(new EldritchMarshRifleAbility(), new EldritchItemAbility(), new EldritchMarshSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Obed Marsh's Rifle";
    }

    @Override
    public String getDescription() {
        return "The Terror of Devil Reef.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "2.2";
    }

    @Override
    public String getIcon() {
        return "eldritch_rifle";
    }

    @Override
    public int getItemLevel() {
        return 45;
    }

    @Override
    public boolean isEldritch() {
        return true;
    }

    @Override
    public boolean isDropable() {
        return false;
    }
}
