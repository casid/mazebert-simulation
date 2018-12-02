package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class DrinkPotionTest extends UsecaseTest<DrinkPotionCommand> {
    Tower tower;
    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        usecase = new DrinkPotion();

        request.playerId = 1;
        request.potionType = PotionType.CommonDamage;
        request.towerX = 4;
        request.towerY = 6;

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.potionStash.add(request.potionType);
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        tower.setWizard(wizard);
        tower.setX(4);
        tower.setY(6);
        unitGateway.addUnit(tower);
    }

    @Test
    void drink() {
        whenRequestIsExecuted();

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.05f);
    }

    @Test
    void potionNotInStash() {
        wizard.potionStash.remove(request.potionType);
        whenRequestIsExecuted();
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void drinkTwoPotions() {
        wizard.potionStash.add(request.potionType);

        whenRequestIsExecuted();
        whenRequestIsExecuted();

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.1f);
    }

    @Test
    void towerNotFound() {
        request.towerX = 100;
        whenRequestIsExecuted();
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }
}