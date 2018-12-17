package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class BloodDemonBlade extends Item {

    private BloodDemonBladeAbility ability;

    public BloodDemonBlade() {
        super(new BloodDemonBladeAbility());
        ability = getAbility(BloodDemonBladeAbility.class);
    }

    @Override
    public String getName() {
        return "Blood Demon's Blade";
    }

    @Override
    public String getDescription() {
        return "Paid with blood,\nit better be sharp!\n(Only obtainable by summoning)";
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
    public boolean isDark() {
        return true;
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    public void setDamage(float damage) {
        ability.setDamage(damage);
    }
}
