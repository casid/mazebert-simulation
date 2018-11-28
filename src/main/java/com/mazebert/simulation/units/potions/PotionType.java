package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.hash.Hash;

public strictfp enum PotionType implements CardType<Potion> {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST CARDS FROM THIS STASH.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!
    CommonDamage(1, CommonDamage.class),
    CommonSpeed(2, CommonSpeed.class),
    CommonCrit(3, CommonCrit.class),
    Mead(4, Mead.class),
    UncommonDamage(5, UncommonDamage.class),
    UncommonSpeed(6, UncommonSpeed.class),
    UncommonCrit(7, UncommonCrit.class),
    UncommonDrops(8, UncommonDrops.class),
    RareDamage(9, RareDamage.class),
    RareSpeed(10, RareSpeed.class),
    RareCrit(11, RareCrit.class),
    RareDrops(12, RareDrops.class),
    Tears(13, Tears.class),
    Nillos(14, Nillos.class),
    Painkiller(15, Painkiller.class),
    Sacrifice(16, Sacrifice.class),
    EssenceOfWisdom(17, EssenceOfWisdom.class),
    EssenceOfLuck(18, EssenceOfLuck.class),
    AngelicElixir(19, AngelicElixir.class),
    CardDustCrit(20, CardDustCrit.class),
    CardDustLuck(21, CardDustLuck.class),
    CardDustLevel(22, CardDustLevel.class),
    CardDustVital(23, CardDustVital.class),
    DrinkAll(24, DrinkAll.class),
    ;

    private static PotionType[] LOOKUP;

    static {
        int maxId = 0;
        for (PotionType potionType : PotionType.values()) {
            maxId = StrictMath.max(maxId, potionType.id);
        }
        LOOKUP = new PotionType[maxId + 1];
        for (PotionType potionType : PotionType.values()) {
            LOOKUP[potionType.id] = potionType;
        }
    }

    public final int id;
    public final Class<? extends Potion> potionClass;
    public final Potion instance;

    PotionType(int id, Class<? extends Potion> potionClass) {
        this.id = id;
        this.potionClass = potionClass;
        this.instance = create();
    }

    public static PotionType forId(int id) {
        if (id < 0 || id >= LOOKUP.length) {
            return null;
        }
        return LOOKUP[id];
    }

    public static PotionType forPotion(Potion potion) {
        Class<? extends Potion> potionClass = potion.getClass();
        for (PotionType potionType : values()) {
            if (potionType.potionClass == potionClass) {
                return potionType;
            }
        }
        return null;
    }

    @Override
    public Potion instance() {
        return instance;
    }

    @Override
    public Potion create() {
        try {
            return potionClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void hash(Hash hash) {
        hash.add(id);
    }

}
