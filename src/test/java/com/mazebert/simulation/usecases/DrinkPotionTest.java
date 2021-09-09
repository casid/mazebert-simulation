package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class DrinkPotionTest extends UsecaseTest<DrinkPotionCommand> {
    TestMap map;
    Tower tower;
    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();

        usecase = new DrinkPotion();

        command.playerId = 1;
        command.potionType = PotionType.CommonDamage;
        command.towerX = 4;
        command.towerY = 6;

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.potionStash.add(command.potionType);
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        tower.setWizard(wizard);
        tower.setX(4);
        tower.setY(6);
        unitGateway.addUnit(tower);

        gameGateway.getGame().map = map = new TestMap(1);
    }

    @Test
    void drink() {
        whenRequestIsExecuted();

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.05f);
    }

    @Test
    void potionNotInStash() {
        wizard.potionStash.remove(command.potionType);
        whenRequestIsExecuted();
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void drinkTwoPotions() {
        wizard.potionStash.add(command.potionType);

        whenRequestIsExecuted();
        whenRequestIsExecuted();

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.1f);
    }

    @Test
    void drinkAllAtOnce() {
        wizard.potionStash.add(command.potionType);
        command.all = true;

        whenRequestIsExecuted();

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.1f);
    }

    @Test
    void towerNotFound() {
        command.towerX = 100;
        whenRequestIsExecuted();
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void goldenGrounds_potionNotOwned() {
        map.givenMapType(MapType.GoldenGrounds);
        whenRequestIsExecuted();
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void goldenGrounds_potionOwned() {
        wizard.foilPotions.add(command.potionType);
        map.givenMapType(MapType.GoldenGrounds);
        whenRequestIsExecuted();
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.05f);
    }

    @Override
    protected DrinkPotionCommand createCommand() {
        return new DrinkPotionCommand();
    }
}