package com.mazebert.simulation.units.items;

import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class NecronomiconTest extends ItemTest {
    @BeforeEach
    void setUp() {
        waveGateway = new WaveGateway();
        damageSystemTrainer.givenConstantDamage(1000); // one shot!

        whenItemIsEquipped(ItemType.Necronomicon);
    }

    @Test
    void ownTowerKillsCultist() {
        Creep creep = a(creep().cultist());
        unitGateway.addUnit(creep);

        damageSystemTrainer.dealDamage(tower, tower, creep);

        assertThat(tower.getItem(0).getAbility(NecronomiconSummonAbility.class).getSouls()).isEqualTo(51);
    }

    @Test
    void otherTowerKillsCultist() {
        Creep creep = a(creep().cultist());
        unitGateway.addUnit(creep);
        tower.setWizard(new Wizard());

        damageSystemTrainer.dealDamage(tower, tower, creep);

        assertThat(tower.getItem(0).getAbility(NecronomiconSummonAbility.class).getSouls()).isEqualTo(50);
    }

    @Test
    void dropWorks() {
        whenItemIsUnequipped();
    }

    @Test
    void sellWorks() {
        whenTowerIsSold();
    }
}