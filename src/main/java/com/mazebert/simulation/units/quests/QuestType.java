package com.mazebert.simulation.units.quests;

public strictfp enum QuestType {
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST QUESTS.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!

    KillChallengesQuestId(1, KillChallengesQuest.class),
    OnlyDarknessAndNatureQuestId(2, OnlyDarknessAndNatureQuest.class),
    OnlyNatureAndMetropolisQuestId(3, OnlyNatureAndMetropolisQuest.class),
    Map1VictoryQuestId(4, Map1VictoryQuest.class),
    Map2VictoryQuestId(5, Map2VictoryQuest.class),
    WatchCreditsQuestId(6, WatchCreditsQuest.class),
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
    public final Class<? extends Quest> questClass;

    QuestType(int id, Class<? extends Quest> questClass) {
        this.id = id;
        this.questClass = questClass;
    }

    public Quest create() {
        try {
            return questClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static QuestType[] LOOKUP;

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
