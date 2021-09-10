package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class DamageSystem_ArmorLookupTest {

    @Test
    void bounds() {
        Sim.context().version = Sim.version; // ensure damage system is initialized correctly

        DamageSystem damageSystem = new DamageSystem();

        assertThat( damageSystem.getArmorDamageFactor(-500)).isEqualTo(1.981976626733689);
        assertThat( damageSystem.getArmorDamageFactor(-501)).isEqualTo(1.981976626733689);
        assertThat( damageSystem.getArmorDamageFactor(-1)).isEqualTo(1.008);
        assertThat( damageSystem.getArmorDamageFactor(0)).isEqualTo(1.0);
        assertThat( damageSystem.getArmorDamageFactor(+1)).isEqualTo(0.996);
        assertThat( damageSystem.getArmorDamageFactor(+500)).isEqualTo(0.5090116866331555);
        assertThat( damageSystem.getArmorDamageFactor(+501)).isEqualTo(0.5090116866331555);
    }
}