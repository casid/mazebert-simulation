package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.hash.Hash;

public strictfp enum HeroType implements CardType<Hero> {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST CARDS FROM THIS STASH.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!
    LittleFinger(1, LittleFinger.class),
    ShadowMane(2, ShadowMane.class),
    Lycaon(3, Lycaon.class),
    Roderic(4, Roderic.class),
    CookieMonster(5, CookieMonster.class),
    InnKeeper(6, InnKeeper.class),
    HoradricMage(7, HoradricMage.class),
    JesterKing(8, JesterKing.class),
    Kvothe(9, Kvothe.class),
    LoanShark(10, LoanShark.class),
    JackInTheBox(11, JackInTheBox.class),
    Bookworm(12, Bookworm.class),
    ProphetLucien(13, ProphetLucien.class),
    Cthulhu(14, Cthulhu.class),
    Dagon(15, Dagon.class),
    ;

    public final int id;
    public final Class<? extends Hero> heroClass;

    HeroType(int id, Class<? extends Hero> heroClass) {
        this.id = id;
        this.heroClass = heroClass;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Hero instance() {
        Hero instance = Sim.context().heroInstances.get(this);
        if (instance == null) {
            instance = create();
            Sim.context().heroInstances.put(this, instance);
        }
        return instance;
    }

    @Override
    public Hero create() {
        try {
            return heroClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final HeroType[] LOOKUP;
    private static final HeroType[] STANDARD =        {LittleFinger, ShadowMane, Lycaon, Roderic, CookieMonster, InnKeeper, HoradricMage, JesterKing, Kvothe, LoanShark, JackInTheBox, Bookworm};
    private static final HeroType[] DAWN_OF_LIGHT =   {LittleFinger, ShadowMane, Lycaon, Roderic, CookieMonster, InnKeeper, HoradricMage, JesterKing, Kvothe, LoanShark, JackInTheBox, Bookworm, ProphetLucien};
    private static final HeroType[] RISE_OF_CTHULHU = {LittleFinger, ShadowMane, Lycaon, Roderic, CookieMonster, InnKeeper, HoradricMage, JesterKing, Kvothe, LoanShark, JackInTheBox, Bookworm, ProphetLucien, Cthulhu, Dagon};

    static {
        int maxId = 0;
        for (HeroType heroType : HeroType.values()) {
            maxId = StrictMath.max(maxId, heroType.id);
        }
        LOOKUP = new HeroType[maxId + 1];
        for (HeroType heroType : HeroType.values()) {
            LOOKUP[heroType.id] = heroType;
        }
    }

    public static HeroType[] getValues() {
        if (Sim.isRoCSeasonContent()) {
            return RISE_OF_CTHULHU;
        }
        if (Sim.isDoLSeasonContent()) {
            return DAWN_OF_LIGHT;
        }
        return STANDARD;
    }

    public static HeroType forId(int id) {
        return LOOKUP[id];
    }

    public static HeroType forHero(Hero hero) {
        Class<? extends Hero> heroClass = hero.getClass();
        for (HeroType heroType : values()) {
            if (heroType.heroClass == heroClass) {
                return heroType;
            }
        }
        return null;
    }

    @Override
    public void hash(Hash hash) {
        hash.add(id);
    }

    @Override
    public CardCategory category() {
        return CardCategory.Hero;
    }
}
