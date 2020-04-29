package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class BloodDemonBlade extends Item {

    private final BloodDemonBladeAbility ability;

    public BloodDemonBlade() {
        super(new BloodDemonBladeAbility());
        ability = getAbility(BloodDemonBladeAbility.class);
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Blood Demon's Blade";
    }

    @Override
    public String getDescription() {
        return "Paid for with blood,\nit better be sharp!\n(Obtainable only by summoning)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getIcon() {
        return "blood_demon_blade_512";
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
    public Element getElement() {
        return Element.Darkness;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    public void setDamage(float damage) {
        ability.setDamage(damage);
    }
}
