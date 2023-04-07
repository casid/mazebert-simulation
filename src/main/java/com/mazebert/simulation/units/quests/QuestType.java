package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;

public strictfp enum QuestType {
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST QUESTS.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!

    KillChallengesQuest(1, KillChallengesQuest.class),
    OnlyDarknessAndNatureQuest(2, OnlyDarknessAndNatureQuest.class),
    OnlyNatureAndMetropolisQuest(3, OnlyNatureAndMetropolisQuest.class),
    Map1VictoryQuest(4, Map1VictoryQuest.class),
    Map2VictoryQuest(5, Map2VictoryQuest.class),
    WatchCreditsQuest(6, WatchCreditsQuest.class),
    OnlyDarknessAndMetropolisQuest(7, OnlyDarknessAndMetropolisQuest.class),
    OnlyDarknessQuest(8, OnlyDarknessQuest.class),
    OnlyNatureQuest(9, OnlyNatureQuest.class),
    OnlyMetropolisQuest(10, OnlyMetropolisQuest.class),
    Map3VictoryQuest(11, Map3VictoryQuest.class),
    Map4VictoryQuest(12, Map4VictoryQuest.class),
    BonusSurvivalQuest(13, BonusSurvivalQuest.class),
    CollectGoldQuest(14, CollectGoldQuest.class),
    TowerLevelsQuest(15, TowerLevelsQuest.class),
    VisitBlackMarketQuest(16, VisitBlackMarketQuest.class),
    VisitDevelopersInnQuest(17, VisitDevelopersInnQuest.class),
    BuyBlackMarketOfferQuest(18, BuyBlackMarketOfferQuest.class),
    BowlPerfectGameQuest(19, BowlPerfectGameQuest.class),
    BowlStrikesQuest(20, BowlStrikesQuest.class),
    TransmuteUniquesQuest(21, TransmuteUniquesQuest.class),
    TransmuteStackQuest(22, TransmuteStackQuest.class),
    DrinkAllPotionsQuest(23, DrinkAllPotionsQuest.class),
    TransmuteCardsQuest(24, TransmuteCardsQuest.class),
    KnusperHexeQuest(25, KnusperHexeQuest.class),
    BaluQuest(26, BaluQuest.class),
    MuliQuest(27, MuliQuest.class),
    Map4VictoryUnlockReginnQuest(28, Map4VictoryUnlockReginnQuest.class),
    CoopQuest(29, CoopQuest.class),
    HellVictoryQuest(30, HellVictoryQuest.class),
    Map5VictoryQuest(31, Map5VictoryQuest.class),
    Map5VictoryUnlockHeroQuest(32, Map5VictoryUnlockHeroQuest.class),
    OnlyLightQuest(33, OnlyLightQuest.class),
    OnlyDarknessAndLightQuest(34, OnlyDarknessAndLightQuest.class),
    OnlyNatureAndLightQuest(35, OnlyNatureAndLightQuest.class),
    OnlyMetropolisAndLightQuest(36, OnlyMetropolisAndLightQuest.class),
    PlayDoLSeasonGameQuest(37, PlayDoLSeasonGameQuest.class),
    WinDoLSeasonGameQuest(38, WinDoLSeasonGameQuest.class),
    FaceTimeLord(39, FaceTimeLordQuest.class),
    PlayRoCSeasonGameQuest(40, PlayRoCSeasonGameQuest.class),
    WinRoCSeasonGameQuest(41, WinRoCSeasonGameQuest.class),
    KillCultistsOfAzathothQuest(42, KillCultistsOfAzathothQuest.class),
    KillCultistsOfCthulhuQuest(43, KillCultistsOfCthulhuQuest.class),
    KillCultistsOfYigQuest(44, KillCultistsOfYigQuest.class),
    KillCultistsOfDagonQuest(45, KillCultistsOfDagonQuest.class),
    TransferCardQuest(46, TransferCardQuest.class),
    TransferUniqueCardQuest(47, TransferUniqueCardQuest.class),
    WinAprilFoolsGameQuest(48, WinAprilFoolsGameQuest.class),
    PlayRnRSeasonGameQuest(49, PlayRnRSeasonGameQuest.class),
    WinRnRSeasonGameQuest(50, WinRnRSeasonGameQuest.class),
    BeaconQuest(51, BeaconQuest.class),
    UnlockThorQuest(52, UnlockThorQuest.class),
    UnlockLokiQuest(53, UnlockLokiQuest.class),
    UnlockHelQuest(54, UnlockHelQuest.class),
    UnlockIdunQuest(55, UnlockIdunQuest.class),
    NaglfarFailureQuest(56, NaglfarFailureQuest.class),
    NaglfarSuccessQuest(57, NaglfarSuccessQuest.class),
    ;

    private static final QuestType[] STANDARD        = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest};
    private static final QuestType[] DAWN_OF_LIGHT   = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, PlayDoLSeasonGameQuest, WinDoLSeasonGameQuest, FaceTimeLord};
    private static final QuestType[] STANDARD_DOL    = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord};
    private static final QuestType[] ROC             = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord, TransferCardQuest, TransferUniqueCardQuest};
    private static final QuestType[] ROC_APR         = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord, TransferCardQuest, TransferUniqueCardQuest, WinAprilFoolsGameQuest};
    private static final QuestType[] ROC_SEASON      = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord, PlayRoCSeasonGameQuest, WinRoCSeasonGameQuest, KillCultistsOfAzathothQuest, KillCultistsOfCthulhuQuest, KillCultistsOfYigQuest, KillCultistsOfDagonQuest, TransferCardQuest, TransferUniqueCardQuest};
    private static final QuestType[] ROC_SEASON_APR  = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord, PlayRoCSeasonGameQuest, WinRoCSeasonGameQuest, KillCultistsOfAzathothQuest, KillCultistsOfCthulhuQuest, KillCultistsOfYigQuest, KillCultistsOfDagonQuest, TransferCardQuest, TransferUniqueCardQuest, WinAprilFoolsGameQuest};
    private static final QuestType[] STANDARD_ROC    = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord, KillCultistsOfAzathothQuest, KillCultistsOfCthulhuQuest, KillCultistsOfYigQuest, KillCultistsOfDagonQuest, TransferCardQuest, TransferUniqueCardQuest, WinAprilFoolsGameQuest};
    private static final QuestType[] RNR             = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord, KillCultistsOfAzathothQuest, KillCultistsOfCthulhuQuest, KillCultistsOfYigQuest, KillCultistsOfDagonQuest, TransferCardQuest, TransferUniqueCardQuest, WinAprilFoolsGameQuest, PlayRnRSeasonGameQuest, WinRnRSeasonGameQuest, BeaconQuest, UnlockThorQuest, UnlockLokiQuest, UnlockHelQuest, UnlockIdunQuest, NaglfarFailureQuest, NaglfarSuccessQuest};
    private static final QuestType[] STANDARD_RNR    = {KillChallengesQuest, OnlyDarknessAndNatureQuest, OnlyNatureAndMetropolisQuest, Map1VictoryQuest, Map2VictoryQuest, WatchCreditsQuest, OnlyDarknessAndMetropolisQuest, OnlyDarknessQuest, OnlyNatureQuest, OnlyMetropolisQuest, Map3VictoryQuest, Map4VictoryQuest, BonusSurvivalQuest, CollectGoldQuest, TowerLevelsQuest, VisitBlackMarketQuest, VisitDevelopersInnQuest, BuyBlackMarketOfferQuest, BowlPerfectGameQuest, BowlStrikesQuest, TransmuteUniquesQuest, TransmuteStackQuest, DrinkAllPotionsQuest, TransmuteCardsQuest, KnusperHexeQuest, BaluQuest, MuliQuest, Map4VictoryUnlockReginnQuest, CoopQuest, HellVictoryQuest, Map5VictoryQuest, Map5VictoryUnlockHeroQuest, OnlyLightQuest, OnlyDarknessAndLightQuest, OnlyNatureAndLightQuest, OnlyMetropolisAndLightQuest, FaceTimeLord, KillCultistsOfAzathothQuest, KillCultistsOfCthulhuQuest, KillCultistsOfYigQuest, KillCultistsOfDagonQuest, TransferCardQuest, TransferUniqueCardQuest, WinAprilFoolsGameQuest, BeaconQuest, UnlockThorQuest, UnlockLokiQuest, UnlockHelQuest, UnlockIdunQuest, NaglfarFailureQuest, NaglfarSuccessQuest};

    public final int id;
    public final Class<? extends Quest> questClass;

    QuestType(int id, Class<? extends Quest> questClass) {
        this.id = id;
        this.questClass = questClass;
    }

    public Quest create() {
        try {
            return questClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final QuestType[] LOOKUP;

    static {
        int maxId = 0;
        for (QuestType questType : QuestType.values()) {
            maxId = StrictMath.max(maxId, questType.id);
        }
        LOOKUP = new QuestType[maxId + 1];
        for (QuestType questType : QuestType.values()) {
            LOOKUP[questType.id] = questType;
        }
    }

    public static QuestType[] getValues() {
        if (Sim.context().version > Sim.vRnREnd) {
            return STANDARD_RNR;
        }
        if (Sim.context().isRnRSeasonContent()) {
            return RNR;
        }
        if (Sim.context().version >= Sim.vRoCEnd) {
            return STANDARD_ROC;
        }
        if (Sim.context().isRoCSeasonContent()) {
            return Sim.context().version >= Sim.v24 ? ROC_SEASON_APR : ROC_SEASON;
        }
        if (Sim.context().version >= Sim.vRoC) {
            return Sim.context().version >= Sim.v24 ? ROC_APR : ROC;
        }
        if (Sim.context().version >= Sim.vDoLEnd) {
            return STANDARD_DOL;
        }
        if (Sim.context().isDoLSeasonContent()) {
            return DAWN_OF_LIGHT;
        }
        return STANDARD;
    }

    public static QuestType forId(int id) {
        return LOOKUP[id];
    }

    public static QuestType forClass(Class<? extends Quest> questClass) {
        for (QuestType questType : values()) {
            if (questType.questClass == questClass) {
                return questType;
            }
        }
        return null;
    }
}
