package com.mazebert.simulation.units.items;

import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class EldritchChainsawTest extends ItemTest {

    @BeforeEach
    void setUp() {
        waveGateway = new WaveGateway();
    }

    @Test
    void damage() {
        whenItemIsEquipped(ItemType.EldritchChainsaw);

        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        assertThat(creep.getHealth()).isEqualTo(100.0);
        creep.onUpdate.dispatch(1.0f);
        assertThat(creep.getHealth()).isEqualTo(95.99999994039536);
    }

    @Test
    void noDamageWhenUnequipped() {
        whenItemIsEquipped(ItemType.EldritchChainsaw);

        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenItemIsUnequipped();

        assertThat(creep.getHealth()).isEqualTo(100.0);
        creep.onUpdate.dispatch(1.0f);
        assertThat(creep.getHealth()).isEqualTo(100.0);
    }
}