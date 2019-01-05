package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class PoisonArrowTest extends ItemTest {

    @BeforeEach
    void setUp() {
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
    }

    @Test
    void poison() {
        Creep creep = a(creep());
        unitGateway.addUnit(creep);

        whenItemIsEquipped(ItemType.PoisonArrow);
        tower.simulate(tower.getCooldown());

        assertThat(creep.getHealth()).isEqualTo(90);
        creep.simulate(1.0f);
        assertThat(creep.getHealth()).isEqualTo(89.49999999254942);
    }
}