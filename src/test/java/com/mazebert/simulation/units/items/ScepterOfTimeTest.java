package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Simulation;
import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class ScepterOfTimeTest extends ItemTest {

    @BeforeEach
    void setUp() {
        version = Sim.vDoLEnd;
        simulation = new Simulation(false);
    }

    @Test
    void timeModifier_equipped() {
        whenItemIsEquipped(ItemType.ScepterOfTime);
        assertThat(simulation.getRawTimeModifier()).isEqualTo(1); // nothing happens initially
    }

    @Test
    void toggled_2x() {
        whenItemIsEquipped(ItemType.ScepterOfTime);
        whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

        assertThat(simulation.getRawTimeModifier()).isEqualTo(2);
    }

    @Test
    void toggled_3x() {
        whenItemIsEquipped(ItemType.ScepterOfTime);
        simulation.setTimeModifier(2);
        whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

        assertThat(simulation.getRawTimeModifier()).isEqualTo(3);
    }

    @Test
    void toggled_4x() {
        whenItemIsEquipped(ItemType.ScepterOfTime);
        simulation.setTimeModifier(3);
        whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

        assertThat(simulation.getRawTimeModifier()).isEqualTo(4);
    }

    @Test
    void toggled_1x() {
        whenItemIsEquipped(ItemType.ScepterOfTime);
        simulation.setTimeModifier(4);
        whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

        assertThat(simulation.getRawTimeModifier()).isEqualTo(1);
    }

    @Test
    void toggled_whenPaused() {
        simulation.setPause(1, true);
        whenItemIsEquipped(ItemType.ScepterOfTime);
        whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

        assertThat(simulation.getRawTimeModifier()).isEqualTo(2);
    }

    @Nested
    class Notifications {
        SimulationListenersTrainer simulationListenersTrainer = new SimulationListenersTrainer();

        @BeforeEach
        void setUp() {
            simulationListeners = simulationListenersTrainer;
            wizard.name = "Wizard1";
        }

        @Test
        void oneWizard() {
            whenItemIsEquipped(ItemType.ScepterOfTime);
            whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

            simulationListenersTrainer.thenNotificationsAre(wizard, "Wizard1 made time pass 2x faster.");
        }

        @Test
        void twoWizards() {
            Wizard wizard2 = new Wizard();
            wizard2.name = "Wizard2";
            unitGateway.addUnit(wizard2);

            whenItemIsEquipped(ItemType.ScepterOfTime);
            whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

            simulationListenersTrainer.thenNotificationsAre(wizard, "Wizard1 made time pass 2x faster.");
            simulationListenersTrainer.thenNotificationsAre(wizard2, "Wizard1 made time pass 2x faster.");
        }

        @Test
        void normalTime() {
            simulation.setTimeModifier(4);
            whenItemIsEquipped(ItemType.ScepterOfTime);
            whenAbilityIsActivated(tower, ActiveAbilityType.ScepterOfTimeToggle);

            simulationListenersTrainer.thenNotificationsAre(wizard, "Wizard1 changed time back to normal.");
        }
    }
}