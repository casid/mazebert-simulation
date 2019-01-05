package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class MjoelnirTest extends ItemTest {
    @BeforeEach
    void setUp() {
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
    }

    @Test
    void noChainLightningAmokWithSplash() {
        Creep creep1 = a(creep());
        Creep creep2 = a(creep());
        Creep creep3 = a(creep());

        unitGateway.addUnit(creep1);
        unitGateway.addUnit(creep2);
        unitGateway.addUnit(creep3);

        whenItemIsEquipped(ItemType.Mjoelnir, 0);
        whenItemIsEquipped(ItemType.MesserschmidtsReaver, 1);
        tower.simulate(tower.getCooldown());

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(97.5);
    }
}