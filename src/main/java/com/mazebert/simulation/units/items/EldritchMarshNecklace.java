package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.abilities.EldritchItemRareAbility;

public strictfp class EldritchMarshNecklace extends Item {
    public EldritchMarshNecklace() {
        super(new EldritchMarshNecklaceAbility(), new EldritchItemRareAbility(), new EldritchMarshSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.RISE_OF_CTHULHU
        );
    }

    @Override
    public String getName() {
        return "Obed Marsh's Necklace";
    }

    @Override
    public String getDescription() {
        return "Gift of the Old Ones.";
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
        return "eldritch_necklace";
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
