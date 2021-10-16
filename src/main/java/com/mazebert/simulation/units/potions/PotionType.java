package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Sim;
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
    ChangeSex(25, ChangeSex.class),
    ResearchNature(26, ResearchNature.class),
    ResearchMetropolis(27, ResearchMetropolis.class),
    ResearchDarkness(28, ResearchDarkness.class),
    ResearchLight(29, ResearchLight.class),
    WhiteRussian(30, WhiteRussian.class),
    UnicornTears(31, UnicornTears.class),
    LeuchtFeuer(32, Leuchtfeuer.class),
    VikingBlodMead(33, VikingBlodMead.class),
    ;

    private static int maxId;
    private static final PotionType[] LOOKUP;

    private static final PotionType[] STANDARD      = {CommonDamage, CommonSpeed, CommonCrit, Mead, UncommonDamage, UncommonSpeed, UncommonCrit, UncommonDrops, RareDamage, RareSpeed, RareCrit, RareDrops, Tears, Nillos, Painkiller, Sacrifice, EssenceOfWisdom, EssenceOfLuck, AngelicElixir, CardDustCrit, CardDustLuck, CardDustLevel, CardDustVital, DrinkAll, ChangeSex, ResearchNature, ResearchMetropolis, ResearchDarkness};
    private static final PotionType[] STANDARD_DOL  = {CommonDamage, CommonSpeed, CommonCrit, Mead, UncommonDamage, UncommonSpeed, UncommonCrit, UncommonDrops, RareDamage, RareSpeed, RareCrit, RareDrops, Tears, Nillos, Painkiller, Sacrifice, EssenceOfWisdom, EssenceOfLuck, AngelicElixir, CardDustCrit, CardDustLuck, CardDustLevel, CardDustVital, DrinkAll, ChangeSex, ResearchNature, ResearchMetropolis, ResearchDarkness, WhiteRussian};
    private static final PotionType[] DAWN_OF_LIGHT = {CommonDamage, CommonSpeed, CommonCrit, Mead, UncommonDamage, UncommonSpeed, UncommonCrit, UncommonDrops, RareDamage, RareSpeed, RareCrit, RareDrops, Tears, Nillos, Painkiller, Sacrifice, EssenceOfWisdom, EssenceOfLuck, AngelicElixir, CardDustCrit, CardDustLuck, CardDustLevel, CardDustVital, DrinkAll, ChangeSex, ResearchNature, ResearchMetropolis, ResearchDarkness, ResearchLight, WhiteRussian, UnicornTears};
    private static final PotionType[] STANDARD_ROC  = {CommonDamage, CommonSpeed, CommonCrit, Mead, UncommonDamage, UncommonSpeed, UncommonCrit, UncommonDrops, RareDamage, RareSpeed, RareCrit, RareDrops, Tears, Nillos, Painkiller, Sacrifice, EssenceOfWisdom, EssenceOfLuck, AngelicElixir, CardDustCrit, CardDustLuck, CardDustLevel, CardDustVital, DrinkAll, ChangeSex, ResearchNature, ResearchMetropolis, ResearchDarkness, ResearchLight, WhiteRussian, UnicornTears, LeuchtFeuer};
    private static final PotionType[] RNR           = {CommonDamage, CommonSpeed, CommonCrit, Mead, UncommonDamage, UncommonSpeed, UncommonCrit, UncommonDrops, RareDamage, RareSpeed, RareCrit, RareDrops, Tears, Nillos, Painkiller, Sacrifice, EssenceOfWisdom, EssenceOfLuck, AngelicElixir, CardDustCrit, CardDustLuck, CardDustLevel, CardDustVital, DrinkAll, ChangeSex, ResearchNature, ResearchMetropolis, ResearchDarkness, ResearchLight, WhiteRussian, UnicornTears, LeuchtFeuer, VikingBlodMead};

    static {
        maxId = 0;
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

    PotionType(int id, Class<? extends Potion> potionClass) {
        this.id = id;
        this.potionClass = potionClass;
    }

    public static PotionType[] getValues() {
        if (Sim.context().version >= Sim.vRnR) {
            return RNR;
        } else if (Sim.context().version >= Sim.vRoCEnd) {
            return STANDARD_ROC;
        } else if (Sim.isDoLSeasonContent()) {
            return DAWN_OF_LIGHT;
        } else if (Sim.context().version >= Sim.vDoL) {
            return STANDARD_DOL;
        }
        return STANDARD;
    }

    public static boolean isSupported(PotionType potionType) {
        for (PotionType value : getValues()) {
            if (value == potionType) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unused") // By client
    public static int getMaxId() {
        return maxId;
    }

    public static PotionType forId(int id) {
        if (id < 0 || id >= LOOKUP.length) {
            return null;
        }
        return LOOKUP[id];
    }

    public static PotionType forClass(Class<? extends Potion> potionClass) {
        for (PotionType potionType : values()) {
            if (potionType.potionClass == potionClass) {
                return potionType;
            }
        }
        return null;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Potion instance() {
        Potion instance = Sim.context().potionInstances.get(this);
        if (instance == null) {
            instance = create();
            Sim.context().potionInstances.put(this, instance);
        }
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

    @Override
    public CardCategory category() {
        return CardCategory.Potion;
    }

}
