package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnQuestCompletedListener;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FaceTimeLordQuestTest extends SimTest implements OnQuestCompletedListener {

    TestMap map;
    Wizard wizard;
    Quest completedQuest;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();

        map = new TestMap(1);
        map.givenMapType(MapType.BloodMoor);
        gameGateway.getGame().map = map;

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        wizard.onQuestCompleted.add(this);
        wizard.addAbility(new FaceTimeLordQuest());
    }

    @Test
    void timeLordCountDown() {
        simulationListeners.onTimeLordStarted.dispatch();

        assertThat(completedQuest).isNotNull();
    }

    @Test
    void nothing() {
        assertThat(completedQuest).isNull();
    }

    @Test
    void hidden() {
        assertThat(new FaceTimeLordQuest().isHidden()).isTrue();
    }

    @Override
    public void onQuestCompleted(Wizard wizard, Quest quest) {
        completedQuest = quest;
    }
}