package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.maps.Map;
import com.mazebert.simulation.minigames.BowlingGameTrainer;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class BowlingBallTest extends ItemTest {
    BowlingBall item;
    BowlingBallUnit ball;
    Map map;

    @BeforeEach
    void setUp() {
        gameGateway.getGame().map = map = new BloodMoor();
        whenItemIsEquipped(ItemType.BowlingBall);
        item = (BowlingBall) tower.getItem(0);
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
    void rolledOverCreepsAreKilledInstantly_onlyByChance() {
        randomPluginTrainer.givenFloatAbs(0.9f, 0.0f);
        Creep creep = a(creep());
        creep.setX(11);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);
        ball.simulate(0.1f); // second chance is lucky and would kill the creep, but we roll only once per creep

        assertThat(creep.isDead()).isFalse();
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

    @Test
    void score_noCreeps() {
        whenBowlingBallIsRolled();
        ball.simulate(10000.0f);

        assertThat(item.getGame().getScore()).isEqualTo(0);
    }

    @Test
    void score_oneCreep() {
        Creep creep = a(creep());
        creep.setX(11);
        creep.setY(7);
        unitGateway.addUnit(creep);

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);
        ball.simulate(10000.0f);

        assertThat(item.getGame().getScore()).isEqualTo(1);
    }

    @Test
    void perfectGame() {
        difficultyGateway.setDifficulty(Difficulty.Nightmare);
        BowlingGameTrainer trainer = new BowlingGameTrainer(item.getGame());
        trainer.givenSamePinsAreRolled(10, 11);
        for (int i = 0; i < 10; ++i) {
            Creep creep = a(creep());
            creep.setX(11);
            creep.setY(7);
            unitGateway.addUnit(creep);
        }

        whenBowlingBallIsRolled();
        ball.simulate(0.1f);
        ball.simulate(10000.0f);

        assertThat(wizard.experience).isEqualTo(300L);
    }

    private void whenBowlingBallIsRolled() {
        whenAbilityIsActivated(tower, ActiveAbilityType.BowlingBallRollAbility);

        ball = unitGateway.findUnit(BowlingBallUnit.class, wizard.getPlayerId());
    }

    @Test
    void unequip_disposedCorrectly() {
        whenBowlingBallIsRolled();
        whenItemIsEquipped(null, 0);

        simulationListeners.onUpdate.dispatch(BowlingBall.BALL_COOLDOWN);

        assertThat(item.getAbility(BowlingBallRollAbility.class).getReadyProgress()).isEqualTo(1.0f);
    }

    private void thenBallIsRemoved() {
        assertThat(unitGateway.findUnit(BowlingBallUnit.class, wizard.getPlayerId())).isNull();
    }

    private void thenBallIsNotRemoved() {
        assertThat(unitGateway.findUnit(BowlingBallUnit.class, wizard.getPlayerId())).isNotNull();
    }
}