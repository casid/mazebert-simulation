package com.mazebert.simulation.units.quests;

public strictfp enum QuestReward {
    Small(20),
    Medium(40),
    Big(60),
    Bigger(80),
    Huge(100),
    Fortune(250),
    ;

    public final int relics;

    QuestReward(int relics) {
        this.relics = relics;
    }
}
