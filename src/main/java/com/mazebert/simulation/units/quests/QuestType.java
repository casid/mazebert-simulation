package com.mazebert.simulation.units.quests;

public strictfp enum QuestType {
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST QUESTS.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!

    KillChallengesQuestId(1),
//    OnlyDarknessAndNatureQuestId(2),
//    OnlyNatureAndMetropolisQuestId(3),
//    Map1VictoryQuestId(4),
//    Map2VictoryQuestId(5),
//    WatchCreditsQuestId(6),
//    OnlyDarknessAndMetropolisQuestId(7),
//    OnlyDarknessQuestId(8),
//    OnlyNatureQuestId(9),
//    OnlyMetropolisQuestId(10),
//    Map3VictoryQuestId(11),
//    Map4VictoryQuestId(12),
//    BonusSurvivalQuestId(13),
//    CollectGoldQuestId(14),
//    TowerLevelsQuestId(15),
//    VisitBlackMarketQuestId(16),
//    VisitDevelopersInnQuestId(17),
//    BuyBlackMarketOfferQuestId(18),
//    BowlPerfectGameQuestId(19),
//    BowlStrikesQuestId(20),
//    TransmuteUniquesQuestId(21),
//    TransmuteStackQuestId(22),
//    DrinkAllPotionsQuestId(23),
//    TransmuteCardsQuestId(24),
//    KnusperhexeQuestId(25),
//    BaluQuestId(26),
//    MuliQuestId(27),
//    Map4VictoryUnlockReginnQuestId(28),
    // TODO Coop-Quest: Win a game with a friend, you both will be rewarded
    ;


    public final int id;

    QuestType(int id) {
        this.id = id;
    }
}
