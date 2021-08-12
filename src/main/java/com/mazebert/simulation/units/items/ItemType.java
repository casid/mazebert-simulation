package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.units.items.prophecies.CardDustProphecy;
import com.mazebert.simulation.units.items.prophecies.HungoverChallengeProphecy;
import com.mazebert.simulation.units.items.prophecies.WealthyBossProphecy;

public strictfp enum ItemType implements CardType<Item> {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST CARDS FROM THIS STASH.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!
    WoodenStaff(1, WoodenStaff.class),
    LeatherBoots(2, LeatherBoots.class),
    WoodAxe(3, WoodAxe.class),
    BabySword(4, BabySword.class),
    SchoolBook(5, SchoolBook.class),
    WetTowel(6, WetTowel.class),
    Pumpkin(7, Pumpkin.class),
    WarAxe(8, WarAxe.class),
    Handbag(9, Handbag.class),
    GoldCoins(10, GoldCoins.class),
    RingOfGreed(11, RingOfGreed.class),
    LongBow(12, LongBow.class),
    MonsterTeeth(13, MonsterTeeth.class),
    MagicMushroom(14, MagicMushroom.class),
    LuckyPants(15, LuckyPants.class),
    PaintingOfSolea(16, PaintingOfSolea.class),
    MeatMallet(17, MeatMallet.class),
    Beheader(18, Beheader.class),
    SevenLeaguesBoots(19, SevenLeaguesBoots.class),
    FistfulOfSteel(20, FistfulOfSteel.class),
    Cauldron(21, Cauldron.class),
    KeyOfWisdom(22, KeyOfWisdom.class),
    VikingHelmet(23, VikingHelmet.class),
    Barrel(24, Barrel.class),
    Excalibur(25, Excalibur.class),
    HelmOfHades(26, HelmOfHades.class),
    MesserschmidtsReaver(27, MesserschmidtsReaver.class),
    DungeonDoor(28, DungeonDoor.class),
    ScepterOfTime(29, ScepterOfTime.class),
    WeddingRing1(30, WeddingRing1.class),
    WeddingRing2(31, WeddingRing2.class),
    NorlsFurySword(32, NorlsFurySword.class),
    NorlsFuryAmulet(33, NorlsFuryAmulet.class),
    FrozenWater(34, FrozenWater.class),
    FrozenHeart(35, FrozenHeart.class),
    FrozenCandle(36, FrozenCandle.class),
    FrozenBook(37, FrozenBook.class),
    WitheredCactus(38, WitheredCactus.class),
    WitheredToadstool(39, WitheredToadstool.class),
    WitheredBandages(40, WitheredBandages.class),
    ImpatienceWrathWatch(41, ImpatienceWrathWatch.class),
    ImpatienceWrathTrain(42, ImpatienceWrathTrain.class),
    ImpatienceWrathForce(43, ImpatienceWrathForce.class),
    DarkBabySword(44, DarkBabySword.class),
    DarkGoldCoins(45, DarkGoldCoins.class),
    DarkRingOfGreed(46, DarkRingOfGreed.class),
    DarkMeatMallet(47, DarkMeatMallet.class),
    DarkCauldron(48, DarkCauldron.class),
    DarkFistfulOfSteel(49, DarkFistfulOfSteel.class),
    DarkBlade(50, DarkBlade.class),
    Wolfskin(51, Wolfskin.class),
    BloodDemonBlade(52, BloodDemonBlade.class),
    Seelenreisser(53, Seelenreisser.class),
    UnluckyPants(54, UnluckyPants.class),
    SkullOfDarkness(55, SkullOfDarkness.class),
    SpectralDaggers(56, SpectralDaggers.class),
    SpectralCape(57, SpectralCape.class),
    BowlingBall(58, BowlingBall.class),
    LightbladeAcademySword(59, LightbladeAcademySword.class),
    LightbladeAcademyDrone(60, LightbladeAcademyDrone.class),
    TransmuteUniques(61, TransmuteUniques.class),
    TransmuteStack(62, TransmuteStack.class),
    Mjoelnir(63, Mjoelnir.class),
    PoisonArrow(64, PoisonArrow.class),
    Trident(65, Trident.class),
    BranchOfYggdrasilLegacy(66, BranchOfYggdrasilLegacy.class),
    DrinkingHorn(67, DrinkingHorn.class),
    UselessMachine(68, UselessMachine.class),
    Lightbringer(69, Lightbringer.class),
    FatKnightArmor(70, FatKnightArmor.class),
    SnowGlobe(71, SnowGlobe.class),
    GuardLance(72, GuardLance.class),
    HeroicCape(73, HeroicCape.class),
    HeroicMask(74, HeroicMask.class),
    BranchOfYggdrasilNature(75, BranchOfYggdrasilNature.class),
    BranchOfYggdrasilMetropolis(76, BranchOfYggdrasilMetropolis.class),
    BranchOfYggdrasilDarkness(77, BranchOfYggdrasilDarkness.class),
    BranchOfYggdrasilLight(78, BranchOfYggdrasilLight.class),
    ToiletPaper(79, ToiletPaper.class),
    EldritchClam(80, EldritchClam.class),
    EldritchClaw(81, EldritchClaw.class),
    EldritchMarshNecklace(82, EldritchMarshNecklace.class),
    EldritchMarshRifle(83, EldritchMarshRifle.class),
    EldritchPearl(84, EldritchPearl.class),
    EldritchArms(85, EldritchArms.class),
    Necronomicon(86, Necronomicon.class),
    EldritchSoup(87, EldritchSoup.class),
    EldritchChainsaw(88, EldritchChainsaw.class),
    WealthyBossProphecy(89, WealthyBossProphecy.class),
    HungoverChallengeProphecy(90, HungoverChallengeProphecy.class),
    CardDustProphecy(91, CardDustProphecy.class),
    ;

    private static int maxId;
    private static final ItemType[] LOOKUP;

    static {
        maxId = 0;
        for (ItemType itemType : ItemType.values()) {
            maxId = StrictMath.max(maxId, itemType.id);
        }
        LOOKUP = new ItemType[maxId + 1];
        for (ItemType itemType : ItemType.values()) {
            LOOKUP[itemType.id] = itemType;
        }
    }

    public final int id;
    public final Class<? extends Item> itemClass;

    private static final ItemType[] STANDARD        = {WoodenStaff, LeatherBoots, WoodAxe, BabySword, SchoolBook, WetTowel, Pumpkin, WarAxe, Handbag, GoldCoins, RingOfGreed, LongBow, MonsterTeeth, MagicMushroom, LuckyPants, PaintingOfSolea, MeatMallet, Beheader, SevenLeaguesBoots, FistfulOfSteel, Cauldron, KeyOfWisdom, VikingHelmet, Barrel, Excalibur, HelmOfHades, MesserschmidtsReaver, DungeonDoor, ScepterOfTime, WeddingRing1, WeddingRing2, NorlsFurySword, NorlsFuryAmulet, FrozenWater, FrozenHeart, FrozenCandle, FrozenBook, WitheredCactus, WitheredToadstool, WitheredBandages, ImpatienceWrathWatch, ImpatienceWrathTrain, ImpatienceWrathForce, DarkBabySword, DarkGoldCoins, DarkRingOfGreed, DarkMeatMallet, DarkCauldron, DarkFistfulOfSteel, DarkBlade, Wolfskin, BloodDemonBlade, Seelenreisser, UnluckyPants, SkullOfDarkness, SpectralDaggers, SpectralCape, BowlingBall, LightbladeAcademySword, LightbladeAcademyDrone, TransmuteUniques, TransmuteStack, Mjoelnir, PoisonArrow, Trident, BranchOfYggdrasilLegacy};
    private static final ItemType[] STANDARD_CORONA = {WoodenStaff, LeatherBoots, WoodAxe, BabySword, SchoolBook, WetTowel, Pumpkin, WarAxe, Handbag, GoldCoins, RingOfGreed, LongBow, MonsterTeeth, MagicMushroom, LuckyPants, PaintingOfSolea, MeatMallet, Beheader, SevenLeaguesBoots, FistfulOfSteel, Cauldron, KeyOfWisdom, VikingHelmet, Barrel, Excalibur, HelmOfHades, MesserschmidtsReaver, DungeonDoor, ScepterOfTime, WeddingRing1, WeddingRing2, NorlsFurySword, NorlsFuryAmulet, FrozenWater, FrozenHeart, FrozenCandle, FrozenBook, WitheredCactus, WitheredToadstool, WitheredBandages, ImpatienceWrathWatch, ImpatienceWrathTrain, ImpatienceWrathForce, DarkBabySword, DarkGoldCoins, DarkRingOfGreed, DarkMeatMallet, DarkCauldron, DarkFistfulOfSteel, DarkBlade, Wolfskin, BloodDemonBlade, Seelenreisser, UnluckyPants, SkullOfDarkness, SpectralDaggers, SpectralCape, BowlingBall, LightbladeAcademySword, LightbladeAcademyDrone, TransmuteUniques, TransmuteStack, Mjoelnir, PoisonArrow, Trident, BranchOfYggdrasilLegacy, ToiletPaper};
    private static final ItemType[] DOL             = {WoodenStaff, LeatherBoots, WoodAxe, BabySword, SchoolBook, WetTowel, Pumpkin, WarAxe, Handbag, GoldCoins, RingOfGreed, LongBow, MonsterTeeth, MagicMushroom, LuckyPants, PaintingOfSolea, MeatMallet, Beheader, SevenLeaguesBoots, FistfulOfSteel, Cauldron, KeyOfWisdom, VikingHelmet, Barrel, Excalibur, HelmOfHades, MesserschmidtsReaver, DungeonDoor, ScepterOfTime, WeddingRing1, WeddingRing2, NorlsFurySword, NorlsFuryAmulet, FrozenWater, FrozenHeart, FrozenCandle, FrozenBook, WitheredCactus, WitheredToadstool, WitheredBandages, ImpatienceWrathWatch, ImpatienceWrathTrain, ImpatienceWrathForce, DarkBabySword, DarkGoldCoins, DarkRingOfGreed, DarkMeatMallet, DarkCauldron, DarkFistfulOfSteel, DarkBlade, Wolfskin, BloodDemonBlade, Seelenreisser, UnluckyPants, SkullOfDarkness, SpectralDaggers, SpectralCape, BowlingBall, LightbladeAcademySword, LightbladeAcademyDrone, TransmuteUniques, TransmuteStack, Mjoelnir, PoisonArrow, Trident, BranchOfYggdrasilLegacy, DrinkingHorn, UselessMachine, Lightbringer, FatKnightArmor, SnowGlobe, GuardLance, HeroicCape, HeroicMask};
    private static final ItemType[] DOL_CORONA      = {WoodenStaff, LeatherBoots, WoodAxe, BabySword, SchoolBook, WetTowel, Pumpkin, WarAxe, Handbag, GoldCoins, RingOfGreed, LongBow, MonsterTeeth, MagicMushroom, LuckyPants, PaintingOfSolea, MeatMallet, Beheader, SevenLeaguesBoots, FistfulOfSteel, Cauldron, KeyOfWisdom, VikingHelmet, Barrel, Excalibur, HelmOfHades, MesserschmidtsReaver, DungeonDoor, ScepterOfTime, WeddingRing1, WeddingRing2, NorlsFurySword, NorlsFuryAmulet, FrozenWater, FrozenHeart, FrozenCandle, FrozenBook, WitheredCactus, WitheredToadstool, WitheredBandages, ImpatienceWrathWatch, ImpatienceWrathTrain, ImpatienceWrathForce, DarkBabySword, DarkGoldCoins, DarkRingOfGreed, DarkMeatMallet, DarkCauldron, DarkFistfulOfSteel, DarkBlade, Wolfskin, BloodDemonBlade, Seelenreisser, UnluckyPants, SkullOfDarkness, SpectralDaggers, SpectralCape, BowlingBall, LightbladeAcademySword, LightbladeAcademyDrone, TransmuteUniques, TransmuteStack, Mjoelnir, PoisonArrow, Trident, BranchOfYggdrasilLegacy, DrinkingHorn, UselessMachine, Lightbringer, FatKnightArmor, SnowGlobe, GuardLance, HeroicCape, HeroicMask, ToiletPaper};
    private static final ItemType[] DOL_END         = {WoodenStaff, LeatherBoots, WoodAxe, BabySword, SchoolBook, WetTowel, Pumpkin, WarAxe, Handbag, GoldCoins, RingOfGreed, LongBow, MonsterTeeth, MagicMushroom, LuckyPants, PaintingOfSolea, MeatMallet, Beheader, SevenLeaguesBoots, FistfulOfSteel, Cauldron, KeyOfWisdom, VikingHelmet, Barrel, Excalibur, HelmOfHades, MesserschmidtsReaver, DungeonDoor, ScepterOfTime, WeddingRing1, WeddingRing2, NorlsFurySword, NorlsFuryAmulet, FrozenWater, FrozenHeart, FrozenCandle, FrozenBook, WitheredCactus, WitheredToadstool, WitheredBandages, ImpatienceWrathWatch, ImpatienceWrathTrain, ImpatienceWrathForce, DarkBabySword, DarkGoldCoins, DarkRingOfGreed, DarkMeatMallet, DarkCauldron, DarkFistfulOfSteel, DarkBlade, Wolfskin, BloodDemonBlade, Seelenreisser, UnluckyPants, SkullOfDarkness, SpectralDaggers, SpectralCape, BowlingBall, LightbladeAcademySword, LightbladeAcademyDrone, TransmuteUniques, TransmuteStack, Mjoelnir, PoisonArrow, Trident, /*                     */DrinkingHorn, UselessMachine, Lightbringer, FatKnightArmor, SnowGlobe, GuardLance, HeroicCape, HeroicMask, BranchOfYggdrasilNature, BranchOfYggdrasilMetropolis, BranchOfYggdrasilDarkness, BranchOfYggdrasilLight, ToiletPaper};
    private static final ItemType[] ROC             = {WoodenStaff, LeatherBoots, WoodAxe, BabySword, SchoolBook, WetTowel, Pumpkin, WarAxe, Handbag, GoldCoins, RingOfGreed, LongBow, MonsterTeeth, MagicMushroom, LuckyPants, PaintingOfSolea, MeatMallet, Beheader, SevenLeaguesBoots, FistfulOfSteel, Cauldron, KeyOfWisdom, VikingHelmet, Barrel, Excalibur, HelmOfHades, MesserschmidtsReaver, DungeonDoor, ScepterOfTime, WeddingRing1, WeddingRing2, NorlsFurySword, NorlsFuryAmulet, FrozenWater, FrozenHeart, FrozenCandle, FrozenBook, WitheredCactus, WitheredToadstool, WitheredBandages, ImpatienceWrathWatch, ImpatienceWrathTrain, ImpatienceWrathForce, DarkBabySword, DarkGoldCoins, DarkRingOfGreed, DarkMeatMallet, DarkCauldron, DarkFistfulOfSteel, DarkBlade, Wolfskin, BloodDemonBlade, Seelenreisser, UnluckyPants, SkullOfDarkness, SpectralDaggers, SpectralCape, BowlingBall, LightbladeAcademySword, LightbladeAcademyDrone, TransmuteUniques, TransmuteStack, Mjoelnir, PoisonArrow, Trident, /*                     */DrinkingHorn, UselessMachine, Lightbringer, FatKnightArmor, SnowGlobe, GuardLance, HeroicCape, HeroicMask, BranchOfYggdrasilNature, BranchOfYggdrasilMetropolis, BranchOfYggdrasilDarkness, BranchOfYggdrasilLight, ToiletPaper, EldritchClam, EldritchClaw, EldritchMarshNecklace, EldritchMarshRifle, EldritchPearl, EldritchArms, Necronomicon, EldritchSoup, EldritchChainsaw};
    private static final ItemType[] RNR             = {WoodenStaff, LeatherBoots, WoodAxe, BabySword, SchoolBook, WetTowel, Pumpkin, WarAxe, Handbag, GoldCoins, RingOfGreed, LongBow, MonsterTeeth, MagicMushroom, LuckyPants, PaintingOfSolea, MeatMallet, Beheader, SevenLeaguesBoots, FistfulOfSteel, Cauldron, KeyOfWisdom, VikingHelmet, Barrel, Excalibur, HelmOfHades, MesserschmidtsReaver, DungeonDoor, ScepterOfTime, WeddingRing1, WeddingRing2, NorlsFurySword, NorlsFuryAmulet, FrozenWater, FrozenHeart, FrozenCandle, FrozenBook, WitheredCactus, WitheredToadstool, WitheredBandages, ImpatienceWrathWatch, ImpatienceWrathTrain, ImpatienceWrathForce, DarkBabySword, DarkGoldCoins, DarkRingOfGreed, DarkMeatMallet, DarkCauldron, DarkFistfulOfSteel, DarkBlade, Wolfskin, BloodDemonBlade, Seelenreisser, UnluckyPants, SkullOfDarkness, SpectralDaggers, SpectralCape, BowlingBall, LightbladeAcademySword, LightbladeAcademyDrone, TransmuteUniques, TransmuteStack, Mjoelnir, PoisonArrow, Trident, /*                     */DrinkingHorn, UselessMachine, Lightbringer, FatKnightArmor, SnowGlobe, GuardLance, HeroicCape, HeroicMask, BranchOfYggdrasilNature, BranchOfYggdrasilMetropolis, BranchOfYggdrasilDarkness, BranchOfYggdrasilLight, ToiletPaper, EldritchClam, EldritchClaw, EldritchMarshNecklace, EldritchMarshRifle, EldritchPearl, EldritchArms, Necronomicon, EldritchSoup, EldritchChainsaw, WealthyBossProphecy, HungoverChallengeProphecy, CardDustProphecy};

    ItemType(int id, Class<? extends Item> itemClass) {
        this.id = id;
        this.itemClass = itemClass;
    }

    public static ItemType[] getValues() {
        if (Sim.isRnRSeasonContent()) {
            return RNR;
        }
        if (Sim.isRoCSeasonContent()) {
            return ROC;
        }
        if (Sim.context().version >= Sim.vDoLEnd) {
            return DOL_END;
        }
        if (Sim.context().version >= Sim.vCorona) {
            if (Sim.isDoLSeasonContent()) {
                return DOL_CORONA;
            } else {
                return STANDARD_CORONA;
            }
        }
        if (Sim.isDoLSeasonContent()) {
            return DOL;
        }
        return STANDARD;
    }

    @SuppressWarnings("unused") // By client
    public static int getMaxId() {
        return maxId;
    }

    public static ItemType forId(int id) {
        if (id < 0 || id >= LOOKUP.length) {
            return null;
        }
        return LOOKUP[id];
    }

    public static ItemType forClass(Class<? extends Item> itemClass) {
        for (ItemType itemType : values()) {
            if (itemType.itemClass == itemClass) {
                return itemType;
            }
        }
        return null;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Item instance() {
        Item instance = Sim.context().itemInstances.get(this);
        if (instance == null) {
            instance = create();
            Sim.context().itemInstances.put(this, instance);
        }
        return instance;
    }

    @Override
    public Item create() {
        try {
            return itemClass.newInstance();
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
        return CardCategory.Item;
    }

}
