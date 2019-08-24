package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class TimeLordEffectTest extends SimTest {

    Game game;

    Wizard wizard1;
    Wizard wizard2;

    Creep creep;
    TimeLordEffect timeLordEffect;
    TimeLordArmorEffect timeLordArmorEffect;

    @BeforeEach
    void setUp() {
        formatPlugin = new FormatPlugin();
        simulationListeners = new SimulationListenersTrainer();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        difficultyGateway = new DifficultyGateway();
        experienceSystem = new ExperienceSystem();
        waveGateway = new WaveGateway();
        waveSpawner = new WaveSpawner();

        unitGateway.addUnit(wizard1 = new Wizard());
        unitGateway.addUnit(wizard2 = new Wizard());

        game = gameGateway.getGame();
        game.bonusRoundSeconds = 5000;

        creep = waveSpawner.createTimeLord();
        timeLordEffect = creep.getAbility(TimeLordEffect.class);
        timeLordArmorEffect = creep.getAbility(TimeLordArmorEffect.class);
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
        assertThat(creep.getWave().armorType).isEqualTo(ArmorType.Zod);
        creep.onUpdate.dispatch(TimeLordArmorEffect.TOGGLE_INTERVAL);
        assertThat(creep.getWave().armorType).isEqualTo(ArmorType.Ber);
        creep.onUpdate.dispatch(TimeLordArmorEffect.TOGGLE_INTERVAL);
        assertThat(creep.getWave().armorType).isEqualTo(ArmorType.Fal);
        creep.onUpdate.dispatch(TimeLordArmorEffect.TOGGLE_INTERVAL);
        assertThat(creep.getWave().armorType).isEqualTo(ArmorType.Vex);
        creep.onUpdate.dispatch(TimeLordArmorEffect.TOGGLE_INTERVAL);
        assertThat(creep.getWave().armorType).isEqualTo(ArmorType.Zod);
    }

    @Test
    void togglesArmorType_notYet() {
        assertThat(creep.getWave().armorType).isEqualTo(ArmorType.Zod);
        creep.onUpdate.dispatch(TimeLordArmorEffect.TOGGLE_INTERVAL - 0.01f);
        assertThat(creep.getWave().armorType).isEqualTo(ArmorType.Zod);
    }

    @Test
    void damageConvertedToBonusRoundSeconds() {
        timeLordEffect.onDamage(null, null, 1000000, 1);
        timeLordEffect.onDamage(null, null, 1000000, 1);
        timeLordEffect.onDamage(null, null, 1000000, 1);
        timeLordEffect.onDamage(null, null, 1000000, 1);
        timeLordEffect.onDamage(null, null, 1000000, 1);
        timeLordEffect.onUpdate(1.0f);

        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(5030);
        assertThat(wizard1.experience).isEqualTo(209);
        assertThat(wizard2.experience).isEqualTo(209);
    }

    @Test
    void damageConvertedToBonusRoundSeconds_noDamage() {
        timeLordEffect.onUpdate(1.0f);

        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(5000);
        assertThat(wizard1.experience).isEqualTo(0);
        assertThat(wizard2.experience).isEqualTo(0);
    }

    @Test
    void damageConvertedToBonusRoundSeconds_bigHit() {
        timeLordEffect.onDamage(null, null, 100000000, 1);
        timeLordEffect.onUpdate(1.0f);

        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(5180);
    }

    @Test
    void damageConvertedToBonusRoundSeconds_bonusTimeAdvancedOnItsOwn() {
        game.bonusRoundSeconds = 5030;
        timeLordEffect.onUpdate(1.0f);

        assertThat(wizard1.experience).isEqualTo(209);
        assertThat(wizard2.experience).isEqualTo(209);
    }

    @Test
    void spawnsCreeps() {
        // TODO spawns random bonus round creeps next to him, if not past a certain map threshold
    }
}