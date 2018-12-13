package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.ArmorType;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class ShadowTest extends SimTest {
    DamageSystemTrainer damageSystemTrainer = new DamageSystemTrainer();
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Shadow shadow;
    Creep creep;
    Wave wave;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();

        randomPlugin = randomPluginTrainer;

        damageSystem = damageSystemTrainer;
        damageSystemTrainer.givenConstantDamage(0);

        shadow = new Shadow();
        unitGateway.addUnit(shadow);

        creep = new Creep();
        wave = new Wave();
        creep.setWave(wave);
        unitGateway.addUnit(creep);
    }

    @Test
    void initial() {
        thenNothingIsAdapted();
    }

    @Test
    void adaptChance() {
        randomPluginTrainer.givenFloatAbs(0.06f);
        whenShadowAttacks();
        thenNothingIsAdapted();
    }

    @Test
    void adaptChance_levelBonus() {
        shadow.setLevel(99);
        randomPluginTrainer.givenFloatAbs(0.06f);
        whenShadowAttacks();
        thenBerIsAdapted();
    }

    @Test
    void adaptBer() {
        wave.armorType = ArmorType.Ber;
        whenShadowAttacks();
        thenBerIsAdapted();
    }

    @Test
    void adaptFal() {
        wave.armorType = ArmorType.Fal;
        whenShadowAttacks();
        thenFalIsAdapted();
    }

    @Test
    void adaptVex() {
        wave.armorType = ArmorType.Vex;
        whenShadowAttacks();
        thenVexIsAdapted();
    }

    @Test
    void adaptBer_Max() {
        wave.armorType = ArmorType.Ber;
        for (int i = 0; i < 4000; ++i) {
            whenShadowAttacks();
        }
        assertThat(shadow.getDamageAgainstBer()).isEqualTo(101.01752f);
    }

    @Test
    void adaptFal_Max() {
        wave.armorType = ArmorType.Fal;
        for (int i = 0; i < 4000; ++i) {
            whenShadowAttacks();
        }
        assertThat(shadow.getDamageAgainstFal()).isEqualTo(101.01752f);
    }

    @Test
    void adaptVex_Max() {
        wave.armorType = ArmorType.Vex;
        for (int i = 0; i < 4000; ++i) {
            whenShadowAttacks();
        }
        assertThat(shadow.getDamageAgainstVex()).isEqualTo(101.01752f);
    }

    @Test
    void adaptCanGoUpAgainAfterMaxIsReached() {
        wave.armorType = ArmorType.Vex;
        for (int i = 0; i < 4000; ++i) {
            whenShadowAttacks();
        }
        wave.armorType = ArmorType.Ber;
        whenShadowAttacks();
        whenShadowAttacks();
        whenShadowAttacks();

        assertThat(shadow.getDamageAgainstVex()).isEqualTo(100.98751f);
        wave.armorType = ArmorType.Vex;
        whenShadowAttacks();
        assertThat(shadow.getDamageAgainstVex()).isEqualTo(101.01751f);
    }

    @Test
    void adaptZod_randomArmorType_Ber() {
        wave.armorType = ArmorType.Zod;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.1f);

        whenShadowAttacks();

        thenBerIsAdapted();
    }

    @Test
    void adaptZod_randomArmorType_Fal() {
        wave.armorType = ArmorType.Zod;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.4f);

        whenShadowAttacks();

        thenFalIsAdapted();
    }

    @Test
    void adaptZod_randomArmorType_Vex() {
        wave.armorType = ArmorType.Zod;
        randomPluginTrainer.givenFloatAbs(0.0f, 0.7f);

        whenShadowAttacks();

        thenVexIsAdapted();
    }

    private void whenShadowAttacks() {
        shadow.simulate(shadow.getBaseCooldown());
        projectileGateway.simulate(0.1f);
        projectileGateway.simulate(0.1f);
    }

    private void thenNothingIsAdapted() {
        assertThat(shadow.getDamageAgainstBer()).isEqualTo(1);
        assertThat(shadow.getDamageAgainstFal()).isEqualTo(1);
        assertThat(shadow.getDamageAgainstVex()).isEqualTo(1);
    }

    private void thenBerIsAdapted() {
        assertThat(shadow.getDamageAgainstBer()).isEqualTo(1.03f);
        assertThat(shadow.getDamageAgainstFal()).isEqualTo(0.99f);
        assertThat(shadow.getDamageAgainstVex()).isEqualTo(0.99f);
    }

    private void thenVexIsAdapted() {
        assertThat(shadow.getDamageAgainstBer()).isEqualTo(0.99f);
        assertThat(shadow.getDamageAgainstFal()).isEqualTo(0.99f);
        assertThat(shadow.getDamageAgainstVex()).isEqualTo(1.03f);
    }

    private void thenFalIsAdapted() {
        assertThat(shadow.getDamageAgainstBer()).isEqualTo(0.99f);
        assertThat(shadow.getDamageAgainstFal()).isEqualTo(1.03f);
        assertThat(shadow.getDamageAgainstVex()).isEqualTo(0.99f);
    }
}