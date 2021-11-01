package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.systems.ProphecySystem;
import com.mazebert.simulation.units.items.*;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThorTest extends SimTest {

    Wizard wizard1;
    Wizard wizard2;

    PlayerGatewayTrainer playerGatewayTrainer;

    @Override
    protected void initVersion() {
        version = Sim.vRnR;
    }

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        playerGatewayTrainer = new PlayerGatewayTrainer();
        playerGatewayTrainer.givenPlayerCount(2);
        playerGateway = playerGatewayTrainer;
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new TestMap(2);
        formatPlugin = new FormatPlugin();
        prophecySystem = new ProphecySystem();
        lootSystem = new LootSystem();

        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wizard1 = new Wizard();
        wizard1.playerId = 1;
        wizard1.addGold(100000);
        unitGateway.addUnit(wizard1);

        wizard2 = new Wizard();
        wizard2.playerId = 2;
        unitGateway.addUnit(wizard2);
    }

    @Test
    void notYetDropped() {
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);
        assertThat(thor.getItem(0)).isInstanceOf(Mjoelnir.class);
    }

    @Test
    void alreadyDropped_inventory() {
        wizard1.itemStash.add(ItemType.Mjoelnir);

        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        assertThat(thor.getItem(0)).isInstanceOf(Mjoelnir.class);
        assertThat(wizard1.itemStash.size()).isEqualTo(0);
    }

    @Test
    void alreadyDropped_otherTowerReplaced() {
        Tower rabbit = whenTowerIsBuilt(wizard1, TowerType.Rabbit, 0, 0);
        whenItemIsEquipped(rabbit, ItemType.Mjoelnir, 3);
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        assertThat(thor.getItem(3)).isInstanceOf(Mjoelnir.class);
        assertThat(wizard1.itemStash.size()).isEqualTo(0);
    }

    @Test
    void alreadyDropped_otherTowerOwned() {
        Tower rabbit = whenTowerIsBuilt(wizard1, TowerType.Rabbit, 1, 0);
        whenItemIsEquipped(rabbit, ItemType.Mjoelnir, 3);
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        assertThat(thor.getItem(0)).isInstanceOf(Mjoelnir.class);
        assertThat(rabbit.getItem(3)).isNull();
        assertThat(wizard1.itemStash.size()).isEqualTo(0);
    }

    @Test
    void alreadyDropped_givenToOtherPlayer() {
        wizard1.itemStash.add(ItemType.Mjoelnir);
        whenCardIsTransferred(wizard1, wizard2, ItemType.Mjoelnir);
        assertThat(wizard1.itemStash.size()).isEqualTo(0);
        assertThat(wizard2.itemStash.size()).isEqualTo(1);

        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        assertThat(thor.getItem(0)).isInstanceOf(Mjoelnir.class);
        assertThat(wizard1.itemStash.size()).isEqualTo(0);
        assertThat(wizard2.itemStash.size()).isEqualTo(1);
    }

    @Test
    void cannotReplaceMjoelnir() {
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        whenItemIsEquipped(thor, ItemType.BabySword, 0);

        assertThat(thor.getItem(0)).isInstanceOf(Mjoelnir.class);
    }

    @Test
    void cannotDropMjoelnir() {
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        whenItemIsEquipped(thor, null, 0);

        assertThat(thor.getItem(0)).isInstanceOf(Mjoelnir.class);
    }

    @Test
    void countsAsViking() {
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        whenItemIsEquipped(thor, ItemType.DrinkingHorn, 1); // Only Vikings can equip that horn

        assertThat(thor.getItem(1)).isInstanceOf(DrinkingHorn.class);
    }

    @Test
    void mjoelnirFlavour() {
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        Item mjoelnir = thor.getItem(0);
        assertThat(mjoelnir.getDescription()).isEqualTo("My precious hammer.");
        assertThat(mjoelnir.getElement()).isEqualTo(Element.Light);
        assertThat(mjoelnir.getAbility(MjoelnirChainAbility.class).getDescription()).isEqualTo("Every hit chain lightning jumps to 6 creeps and deals 32% of the carrier's damage.");
    }

    @Test
    void mjoelnirFlavour_sold() {
        Tower thor = whenTowerIsBuilt(wizard1, TowerType.Thor, 0, 0);

        whenTowerIsSold(thor);

        Item mjoelnir = wizard1.itemStash.get(0).getCard();
        assertThat(mjoelnir.getDescription()).isEqualTo("Thor's mighty hammer.");
        assertThat(mjoelnir.getElement()).isEqualTo(Element.Unknown);
        assertThat(mjoelnir.getAbility(MjoelnirChainAbility.class).getDescription()).isEqualTo("Every second hit chain lightning jumps to 3 creeps and deals 16% of the carrier's damage.");
    }
}