package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public strictfp class YigTest extends SimTest {
    Wizard wizard;
    Yig yig;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        waveGateway = new WaveGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        yig = new Yig();
        yig.setWizard(wizard);
        unitGateway.addUnit(yig);
    }

    @Test
    void startAttributes() {
        assertThat(wizard.health).isEqualTo(2.0f);
        assertThat(waveGateway.getCultistOfYigHealthMultiplier()).isEqualTo(2.0f);
    }

    @Test
    void armorReduction() {
        Creep creep1 = a(creep().cultistOfYig());
        unitGateway.addUnit(creep1);
        creep1.setHealth(0);
        unitGateway.removeUnit(creep1);

        Creep creep2 = a(creep().boss());
        unitGateway.addUnit(creep2);

        assertThat(creep1.getArmor()).isEqualTo(0);
        assertThat(creep2.getArmor()).isEqualTo(-1);
    }

    @Test
    void armorReduction_nonYigKilled() {
        Creep creep1 = a(creep().cultist());
        unitGateway.addUnit(creep1);
        creep1.setHealth(0);
        unitGateway.removeUnit(creep1);

        Creep creep2 = a(creep().boss());
        unitGateway.addUnit(creep2);

        assertThat(creep1.getArmor()).isEqualTo(0);
        assertThat(creep2.getArmor()).isEqualTo(0);
    }
}