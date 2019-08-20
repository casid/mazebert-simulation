package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.SimTest;
import org.junit.jupiter.api.Test;

public strictfp class TimeLordEffectTest extends SimTest {
    @Test
    void slow() {
        // TODO time lord should be pretty slow
    }

    @Test
    void stunResistent() {
        // TODO time lord should be pretty immune to stuns / slows / warps
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