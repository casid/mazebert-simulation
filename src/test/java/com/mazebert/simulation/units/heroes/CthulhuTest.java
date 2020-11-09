package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.usecases.DrinkPotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class CthulhuTest extends SimTest {
    static final float reducedDamagePotionEffect = 0.08f; // Less than usual

    Wizard wizard;
    Cthulhu cthulhu;
    Tower tower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new TestMap(4);
        commandExecutor = new CommandExecutor();
        commandExecutor.addUsecase(new DrinkPotion());

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        cthulhu = new Cthulhu();
        cthulhu.setWizard(wizard);
        unitGateway.addUnit(cthulhu);

        tower = new TestTower();
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);
    }

    @Test
    void oneTower() {
        whenPotionIsConsumed(tower, PotionType.RareDamage);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(reducedDamagePotionEffect);
    }

    @Test
    void threeTowersInRange() {
        Tower tower2 = new TestTower();
        tower2.setX(1);
        tower2.setWizard(wizard);
        unitGateway.addUnit(tower2);

        Tower tower3 = new TestTower();
        tower3.setY(-1);
        tower3.setWizard(wizard);
        unitGateway.addUnit(tower3);

        whenPotionIsConsumed(tower, PotionType.RareDamage);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(reducedDamagePotionEffect);
        assertThat(tower2.getAddedRelativeBaseDamage()).isEqualTo(reducedDamagePotionEffect);
        assertThat(tower3.getAddedRelativeBaseDamage()).isEqualTo(reducedDamagePotionEffect);
    }

    @Test
    void threeTowers_oneNotInRange() {
        Tower tower2 = new TestTower();
        tower2.setX(1);
        tower2.setWizard(wizard);
        unitGateway.addUnit(tower2);

        Tower tower3 = new TestTower();
        tower3.setY(-2);
        tower3.setWizard(wizard);
        unitGateway.addUnit(tower3);

        whenPotionIsConsumed(tower, PotionType.RareDamage);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(reducedDamagePotionEffect);
        assertThat(tower2.getAddedRelativeBaseDamage()).isEqualTo(reducedDamagePotionEffect);
        assertThat(tower3.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void towerOfOtherWizard() {
        Wizard wizard2 = new Wizard();
        wizard2.playerId = 2;
        unitGateway.addUnit(wizard2);

        Tower tower2 = new TestTower();
        tower2.setWizard(wizard2);
        unitGateway.addUnit(tower2);

        whenPotionIsConsumed(tower, PotionType.RareDamage);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(reducedDamagePotionEffect);
        assertThat(tower2.getAddedRelativeBaseDamage()).isEqualTo(0);
    }

    @Test
    void eldritch() {
        assertThat(cthulhu.isEldritch()).isTrue();
    }
}