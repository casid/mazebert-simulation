package com.mazebert.simulation.units.items;

import com.mazebert.simulation.commands.ActivateAbilityCommand;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.maps.Map;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(unitGateway.findUnit(BowlingBallUnit.class, wizard.getPlayerId())).isNull(); // remove when end is reached
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
}