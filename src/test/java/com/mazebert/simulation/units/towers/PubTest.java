package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.ActivateAbilityCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.PubSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.systems.PubSystem.*;
import static org.assertj.core.api.Assertions.assertThat;

strictfp class PubTest extends SimTest {
    Pub pub;
    TestTower otherTower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        pubSystem = new PubSystem();

        pub = new Pub();
        unitGateway.addUnit(pub);

        otherTower = new TestTower();
        unitGateway.addUnit(otherTower);

        commandExecutor = new CommandExecutor();
        commandExecutor.init();
    }

    @Test
    void onePub() {
        whenAbilityIsActivated();
        assertThat(pub.getAddedRelativeBaseDamage()).isEqualTo(0.1f + DAMAGE_BONUS_PER_PUB);
        assertThat(otherTower.getAddedRelativeBaseDamage()).isEqualTo(0.1f + DAMAGE_BONUS_PER_PUB); // bonus for one pub
    }

    @Test
    void threePubs() {
        unitGateway.addUnit(new Pub());
        unitGateway.addUnit(new Pub());

        whenAbilityIsActivated();
        assertThat(pub.getAddedRelativeBaseDamage()).isEqualTo(0.3f + 3 * DAMAGE_BONUS_PER_PUB); // bonus for three pubs
        assertThat(otherTower.getAddedRelativeBaseDamage()).isEqualTo(0.3f + 3 * DAMAGE_BONUS_PER_PUB); // bonus for three pubs
    }

    @Test
    void effectIsRemoved() {
        whenAbilityIsActivated();
        simulationListeners.onUpdate.dispatch(PARTY_TIME);

        assertThat(otherTower.getAddedRelativeBaseDamage()).isEqualTo(0.099999994f);
        assertThat(pub.getAbility(PubParty.class).getReadyProgress()).isEqualTo(0.0f);

        pub.onUpdate.dispatch(0.5f * COOLDOWN_TIME);
        assertThat(pub.getAbility(PubParty.class).getReadyProgress()).isEqualTo(0.5f);

        pub.onUpdate.dispatch(0.5f * COOLDOWN_TIME);
        assertThat(pub.getAbility(PubParty.class).getReadyProgress()).isEqualTo(1.0f);
    }

    @Test
    void onePub_newTowerIsAddedAfterwards() {
        whenAbilityIsActivated();
        TestTower newTower = new TestTower();
        unitGateway.addUnit(newTower);
        simulationListeners.onUpdate.dispatch(PARTY_TIME);

        assertThat(newTower.getAddedRelativeBaseDamage()).isEqualTo(0.1f); // does not suffer after party!
    }

    @Test
    void cannotActivateTwice() {
        whenAbilityIsActivated();
        whenAbilityIsActivated();

        assertThat(pub.getAddedRelativeBaseDamage()).isEqualTo(0.1f + DAMAGE_BONUS_PER_PUB);
        assertThat(otherTower.getAddedRelativeBaseDamage()).isEqualTo(0.1f + DAMAGE_BONUS_PER_PUB);
    }

    private void whenAbilityIsActivated() {
        ActivateAbilityCommand command = new ActivateAbilityCommand();
        command.abilityType = ActiveAbilityType.PubParty;
        commandExecutor.executeVoid(command);
    }
}