package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public strictfp class TimeLordEffectTest extends SimTest {

    Creep creep;

    @BeforeEach
    void setUp() {
        creep = a(creep());
        creep.addAbility(new TimeLordEffect());
    }

    @Test
    void slow() {
        // time lord should be pretty slow
        assertThat(creep.getSpeedModifier()).isEqualTo(0.25f);
    }

    @Test
    void stunResistent() {
        // time lord is immune to stuns / slows / warps
        assertThat(creep.isSteady()).isTrue();
    }

    @Test
    void togglesArmorType() {
        // TODO time lord should toggle its armor type
    }

    @Test
    void damageConvertedToBonusRoundSeconds() {
        // TODO damaging the time lord should grant bonus round seconds / wizard experience
    }

    @Test
    void spawnsCreeps() {
        // TODO spawns random bonus round creeps next to him, if not past a certain map threshold
    }
}