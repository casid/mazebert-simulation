package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;

public strictfp class CookieMonster extends Hero {

    public CookieMonster() {
        addAbility(new CookieMonsterAbility());
        addAbility(new LittleFingerAbility());
    }

    @Override
    public String getName() {
        return "Cookiemonster";
    }

    @Override
    public String getDescription() {
        return "Cookies, cookies, cookies! You have some?";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "cookiemonster_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isSupporterReward() {
        return true;
    }
}
