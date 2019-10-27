package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.hash.Hash;

public strictfp enum TowerType implements CardType<Tower> {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST CARDS FROM THIS STASH.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!
    Beaver(1, Beaver.class),
    Dandelion(2, Dandelion.class),
    Rabbit(3, Rabbit.class),
    Frog(4, Frog.class),
    HerbWitch(5, HerbWitch.class),
    Wolf(6, Wolf.class),
    Huli(7, Huli.class),
    BearHunter(8, BearHunter.class),
    Viking(9, Viking.class),
    Ganesha(10, Ganesha.class),
    Balu(11, Balu.class),
    Manitou(12, Manitou.class),
    Hitman(13, Hitman.class),
    Scientist(14, Scientist.class),
    PocketThief(15, PocketThief.class),
    ElectricChair(16, ElectricChair.class),
    Elvis(17, Elvis.class),
    Pub(18, Pub.class),
    MoneyBin(19, MoneyBin.class),
    MrIron(20, MrIron.class),
    ScarFace(21, ScarFace.class),
    Muli(22, Muli.class),
    Satellite(23, Satellite.class),
    BlackWidow(24, BlackWidow.class),
    Mummy(25, Mummy.class),
    ScareCrow(26, ScareCrow.class),
    Shadow(27, Shadow.class),
    Gib(28, Gib.class),
    KnusperHexe(29, KnusperHexe.class),
    DarkForge(30, DarkForge.class),
    //Cube(31, Cube.class), Cube is no longer in use by now
    AcolyteOfGreed(32, AcolyteOfGreed.class),
    NoviceWizard(33, NoviceWizard.class),
    Spider(34, Spider.class),
    BloodDemon(35, BloodDemon.class),
    Solara(36, Solara.class),
    AbyssKing(37, AbyssKing.class),
    TheRipper(38, TheRipper.class),
    KiwiEgg(39, KiwiEgg.class),
    Kiwi(40, Kiwi.class),
    Stonecutters(41, Stonecutters.class),
    Yggdrasil(42, Yggdrasil.class),
    Adventurer(43, Adventurer.class),
    Gargoyle(44, Gargoyle.class),
    Guard(45, Guard.class),
    TrainingHologram(46, TrainingHologram.class),
    Tinker(47, Tinker.class),
    Templar(48, Templar.class),
    Phoenix(49, Phoenix.class),
    Candle(50, Candle.class),
    // rare 3
    Unicorn(52, Unicorn.class),
    // unique 2
    Lucifer(54, Lucifer.class),
    LuciferFallen(55, LuciferFallen.class),
    // legendary 1
    ;

    public final int id;
    public final Class<? extends Tower> towerClass;

    TowerType(int id, Class<? extends Tower> towerClass) {
        this.id = id;
        this.towerClass = towerClass;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Tower instance() {
        Tower instance = Sim.context().towerInstances.get(this);
        if (instance == null) {
            instance = create();
            Sim.context().towerInstances.put(this, instance);
        }
        return instance;
    }

    @Override
    public Tower create() {
        try {
            Tower tower = towerClass.newInstance();
            tower.setLevel(1);
            return tower;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int maxId;
    private static TowerType[] LOOKUP;

    private static final TowerType[] STANDARD      = {Beaver, Dandelion, Rabbit, Frog, HerbWitch, Wolf, Huli, BearHunter, Viking, Ganesha, Balu, Manitou, Hitman, Scientist, PocketThief, ElectricChair, Elvis, Pub, MoneyBin, MrIron, ScarFace, Muli, Satellite, BlackWidow, Mummy, ScareCrow, Shadow, Gib, KnusperHexe, DarkForge, AcolyteOfGreed, NoviceWizard, Spider, BloodDemon, Solara, AbyssKing, TheRipper, KiwiEgg, Kiwi, Stonecutters, Yggdrasil};
    private static final TowerType[] DAWN_OF_LIGHT = {Beaver, Dandelion, Rabbit, Frog, HerbWitch, Wolf, Huli, BearHunter, Viking, Ganesha, Balu, Manitou, Hitman, Scientist, PocketThief, ElectricChair, Elvis, Pub, MoneyBin, MrIron, ScarFace, Muli, Satellite, BlackWidow, Mummy, ScareCrow, Shadow, Gib, KnusperHexe, DarkForge, AcolyteOfGreed, NoviceWizard, Spider, BloodDemon, Solara, AbyssKing, TheRipper, KiwiEgg, Kiwi, Stonecutters, Yggdrasil, Adventurer, Gargoyle, Guard, TrainingHologram, Tinker, Templar, Phoenix, Candle, Unicorn, Lucifer, LuciferFallen};

    static {
        maxId = 0;
        for (TowerType towerType : TowerType.values()) {
            maxId = StrictMath.max(maxId, towerType.id);
        }
        LOOKUP = new TowerType[maxId + 1];
        for (TowerType towerType : TowerType.values()) {
            LOOKUP[towerType.id] = towerType;
        }
    }

    public static TowerType[] getValues() {
        if (Sim.isDoLSeasonContent()) {
            return DAWN_OF_LIGHT;
        }
        return STANDARD;
    }

    @SuppressWarnings("unused") // By client
    public static int getMaxId() {
        return maxId;
    }

    public static TowerType forId(int id) {
        return LOOKUP[id];
    }

    public static TowerType forClass(Class<? extends Tower> towerClass) {
        for (TowerType towerType : values()) {
            if (towerType.towerClass == towerClass) {
                return towerType;
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
        return CardCategory.Tower;
    }
}
