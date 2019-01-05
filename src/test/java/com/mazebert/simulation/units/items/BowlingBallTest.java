package com.mazebert.simulation.units.items;

import com.mazebert.simulation.commands.ActivateAbilityCommand;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.maps.Map;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class BowlingBallTest extends ItemTest {
    BowlingBallUnit ball;
    Map map;

    @BeforeEach
    void setUp() {
        gameGateway.getGame().map = map = new BloodMoor();
        whenItemIsEquipped(ItemType.BowlingBall);
    }

    @Test
    void ballIsAdded() {
        whenBowlingBallIsRolled();
        assertThat(ball.getX()).isEqualTo(11);
        assertThat(ball.getY()).isEqualTo(7);
    }

    @Test
    void ballMovesTowardsCreepSpawnPoint() {
        whenBowlingBallIsRolled();
        ball.simulate(10000.0f);

        assertThat(ball.getX()).isEqualTo(map.getStartWaypoint().x);
        assertThat(ball.getY()).isEqualTo(map.getStartWaypoint().y);
        thenBallIsRemoved();
    }

    @Test
    void rolledOverCreepsAreKilledInstantly() {
        Creep creep = a(creep());
        creep.setX(11);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);

        assertThat(creep.isDead()).isTrue();
    }

    @Test
    void rolledOverCreepsAreKilledInstantly_evenAfterTowerIsRemoved() {
        Creep creep = a(creep());
        creep.setX(11);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        unitGateway.removeUnit(tower);
        ball.simulate(0.1f);

        assertThat(creep.isDead()).isTrue();
    }

    @Test
    void airCreepsAreIgnored() {
        Creep creep = a(creep().air());
        creep.setX(11);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);

        assertThat(creep.isDead()).isFalse();
        thenBallIsNotRemoved();
    }

    @Test
    void bossesDeflectTheBall() {
        Creep creep = a(creep().boss());
        creep.setX(11);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);

        assertThat(creep.isDead()).isFalse();
        thenBallIsRemoved();
    }

    @Test
    void bossesDeflectTheBall_followedByCreep() {
        Creep boss = a(creep().boss());
        boss.setX(11);
        boss.setY(7);
        unitGateway.addUnit(boss);
        Creep creep = a(creep());
        creep.setX(11);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);

        thenBallIsRemoved();
        assertThat(boss.isDead()).isFalse();
        assertThat(creep.isDead()).isFalse();
    }

    @Test
    void notRolledOverCreepsStayAlive() {
        Creep creep = a(creep());
        creep.setX(13);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);

        assertThat(creep.isDead()).isFalse();
    }

    private void whenBowlingBallIsRolled() {
        ActivateAbilityCommand command = new ActivateAbilityCommand();
        command.playerId = wizard.getPlayerId();
        command.towerX = (int) tower.getX();
        command.towerY = (int) tower.getY();
        command.abilityType = ActiveAbilityType.BowlingBallRollAbility;
        commandExecutor.executeVoid(command);

        ball = unitGateway.findUnit(BowlingBallUnit.class, wizard.getPlayerId());
    }

    private void thenBallIsRemoved() {
        assertThat(unitGateway.findUnit(BowlingBallUnit.class, wizard.getPlayerId())).isNull();
    }

    private void thenBallIsNotRemoved() {
        assertThat(unitGateway.findUnit(BowlingBallUnit.class, wizard.getPlayerId())).isNotNull();
    }
}