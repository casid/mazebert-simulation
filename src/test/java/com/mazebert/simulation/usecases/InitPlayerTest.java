package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.heroes.LittleFinger;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class InitPlayerTest extends UsecaseTest<InitPlayerCommand> {
    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        usecase = new InitPlayer();

        request.playerId = wizard.getPlayerId();
        request.heroType = HeroType.Lycaon;
    }

    @Test
    void littlefinger() {
        request.heroType = HeroType.LittleFinger;

        whenRequestIsExecuted();

        LittleFinger hero = unitGateway.findUnit(LittleFinger.class, wizard.playerId);
        assertThat(hero).isNotNull();
    }

    @Test
    void foilCards() {
        request.foilHeroes = EnumSet.of(HeroType.InnKeeper, HeroType.LoanShark);

        whenRequestIsExecuted();

        assertThat(wizard.foilHeroes).isSameAs(request.foilHeroes);
    }
}