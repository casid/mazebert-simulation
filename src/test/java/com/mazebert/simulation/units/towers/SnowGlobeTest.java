package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class SnowGlobeTest extends SimTest {

    Wizard wizard;

    @BeforeEach
    void setUp() {
        version = Sim.vDoLEnd;

        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = new RandomPluginTrainer();
        lootSystem = new LootSystemTrainer();
        formatPlugin = new FormatPlugin();
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new TestMap(4);
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard = new Wizard();
        wizard.addGold(100_000);
        unitGateway.addUnit(wizard);
    }

    @Test
    void cannotBeBuiltOnItsOwn() {
        whenTowerIsBuilt(TowerType.SnowGlobe);
        assertThat(unitGateway.hasUnits(SnowGlobe.class)).isFalse();
    }

    @Test
    void onlyCommonTowers() {
        givenTowerToReplace(TowerType.Frog);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        assertThat(unitGateway.hasUnits(Frog.class)).isTrue();
        assertThat(unitGateway.hasUnits(SnowGlobe.class)).isFalse();
    }

    @Test
    void descriptionContainsTower() {
        givenTowerToReplace(TowerType.Beaver);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        assertThat(getSnowGlobeItem().getDescription()).isEqualTo("A little <c=#fefefe>Beaver</c> lives in here.");
    }

    @Test
    void beaver() {
        givenTowerToReplace(TowerType.Beaver);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(BeaverStun.class);
    }

    @Test
    void dandelion() {
        givenTowerToReplace(TowerType.Dandelion);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(DandelionSplash.class);
    }

    @Test
    void rabbit() {
        givenTowerToReplace(TowerType.Rabbit);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(RabbitMultishot.class);
    }

    @Test
    void scientist() {
        givenTowerToReplace(TowerType.Scientist);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(ScientistExperience.class);
    }

    @Test
    void pocketThief() {
        givenTowerToReplace(TowerType.PocketThief);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(PocketThiefGold.class);
    }

    @Test
    void mummy() {
        givenTowerToReplace(TowerType.Mummy);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(MummyStumble.class);
    }

    @Test
    void noviceWizard() {
        givenTowerToReplace(TowerType.NoviceWizard);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(AttackSoundAbility.class, NoviceWizardSpell.class);
    }

    @Test
    void spider() {
        givenTowerToReplace(TowerType.Spider);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(AttackSoundAbility.class, SpiderWebDoL.class);
    }

    @Test
    void adventurer() {
        givenTowerToReplace(TowerType.Adventurer);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(AdventurerLight.class);
    }

    @Test
    void gargoyle() {
        givenTowerToReplace(TowerType.Gargoyle);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(AttackSoundAbility.class, GargoyleKnockback.class);
    }

    @Test
    void guard() {
        givenTowerToReplace(TowerType.Guard);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        thenSnowGlobeAbilitiesAre(GuardAura.class);
    }

    @Test
    void nature() {
        givenTowerToReplace(TowerType.Dandelion);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        assertThat(getSnowGlobeItem().isDark()).isFalse();
        assertThat(getSnowGlobeItem().isLight()).isFalse();
    }

    @Test
    void light() {
        givenTowerToReplace(TowerType.Guard);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        assertThat(getSnowGlobeItem().isDark()).isFalse();
        assertThat(getSnowGlobeItem().isLight()).isTrue();
    }

    @Test
    void darkness() {
        givenTowerToReplace(TowerType.NoviceWizard);
        whenTowerIsBuilt(TowerType.SnowGlobe);
        assertThat(getSnowGlobeItem().isDark()).isTrue();
        assertThat(getSnowGlobeItem().isLight()).isFalse();
    }

    @Test
    void guard_bonus() {
        givenTowerToReplace(TowerType.Guard);
        whenTowerIsBuilt(TowerType.SnowGlobe);

        Tower phoenix = whenTowerIsBuilt(wizard, TowerType.Phoenix, 0, 0);
        whenItemIsEquipped(phoenix, ItemType.SnowGlobe);
        whenTowerIsBuilt(wizard, TowerType.Guard, 1, 0);

        assertThat(phoenix.getAddedAbsoluteBaseDamage()).isEqualTo(6);
    }

    @Test
    void guard_rangeBonus() {
        givenTowerToReplace(TowerType.Guard);
        whenTowerIsBuilt(TowerType.SnowGlobe);

        Tower adventurer = whenTowerIsBuilt(wizard, TowerType.Adventurer, 0, 0);
        whenItemIsEquipped(adventurer, ItemType.SnowGlobe);
        whenTowerIsBuilt(wizard, TowerType.Guard, 3, 0);

        assertThat(adventurer.getAddedAbsoluteBaseDamage()).isEqualTo(6);
    }

    @Test
    void guard_rangeBonus_helmOfHades() {
        givenTowerToReplace(TowerType.Guard);
        whenTowerIsBuilt(TowerType.SnowGlobe);

        Tower ripper = whenTowerIsBuilt(wizard, TowerType.TheRipper, 0, 0);
        whenItemIsEquipped(ripper, ItemType.SnowGlobe, 0);
        whenTowerIsBuilt(wizard, TowerType.Guard, 2, 0);
        assertThat(ripper.getAddedAbsoluteBaseDamage()).isEqualTo(0);

        whenItemIsEquipped(ripper, ItemType.HelmOfHades, 1);
        assertThat(ripper.getAddedAbsoluteBaseDamage()).isEqualTo(6);

        whenItemIsEquipped(ripper, null, 1);
        assertThat(ripper.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    @Test
    void guard_itemBonus() {
        givenTowerToReplace(TowerType.Guard);
        whenTowerIsBuilt(TowerType.SnowGlobe);

        Tower adventurer = whenTowerIsBuilt(wizard, TowerType.Adventurer, 0, 0);
        whenItemIsEquipped(adventurer, ItemType.SnowGlobe);
        Tower gargoyle = whenTowerIsBuilt(wizard, TowerType.Gargoyle, 3, 0);
        whenItemIsEquipped(gargoyle, ItemType.GuardLance);

        assertThat(adventurer.getAddedAbsoluteBaseDamage()).isEqualTo(6);

        whenItemIsEquipped(gargoyle, null);

        assertThat(adventurer.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    private void givenTowerToReplace(TowerType towerType) {
        whenTowerIsBuilt(towerType);
    }

    private void whenTowerIsBuilt(TowerType towerType) {
        whenTowerIsBuilt(wizard, towerType, 0, 0);
    }

    private void thenSnowGlobeAbilitiesAre(Class<?>... abilities) {
        assertThat(unitGateway.getAmount(Tower.class)).isZero();

        List<Class<?>> actual = new ArrayList<>();
        Item snowGlobe = getSnowGlobeItem();
        snowGlobe.forEachAbility(a -> actual.add(a.getClass()));
        assertThat(actual).containsExactly(abilities);
    }

    private Item getSnowGlobeItem() {
        return wizard.itemStash.get(ItemType.SnowGlobe).getCard();
    }
}