package com.mazebert.simulation.systems;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DamageSystem_ArmorLookupTest {
    DamageSystem damageSystem = new DamageSystem();

    @Test
    void bounds() {
        assertThat( damageSystem.getArmorDamageFactor(-500)).isEqualTo(1.981976626733689);
        assertThat( damageSystem.getArmorDamageFactor(-501)).isEqualTo(1.981976626733689);
        assertThat( damageSystem.getArmorDamageFactor(-1)).isEqualTo(1.008);
        assertThat( damageSystem.getArmorDamageFactor(0)).isEqualTo(1.0);
        assertThat( damageSystem.getArmorDamageFactor(+1)).isEqualTo(0.996);
        assertThat( damageSystem.getArmorDamageFactor(+500)).isEqualTo(0.5090116866331555);
        assertThat( damageSystem.getArmorDamageFactor(+501)).isEqualTo(0.5090116866331555);
    }
}