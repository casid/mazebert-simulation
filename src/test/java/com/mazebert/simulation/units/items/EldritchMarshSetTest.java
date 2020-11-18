package com.mazebert.simulation.units.items;

import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.Tile;
import com.mazebert.simulation.maps.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EldritchMarshSetTest extends ItemTest {

    TileType waterTile = new TileType(null, 0, 0).water();

    @BeforeEach
    void setUp() {
        waveGateway = new WaveGateway();
    }

    @Test
    void noWaterTiles() {
        whenItemIsEquipped(ItemType.EldritchMarshNecklace);
        assertThat(tower.getItemChance()).isEqualTo(0.7f);
        assertThat(tower.getItemQuality()).isEqualTo(1.0f);
    }

    @Test
    void twoWaterTiles() {
        List<Tile> tiles = gameGateway.getMap().getTiles();
        tiles.set(0, new Tile(waterTile));
        tiles.set(1, new Tile(waterTile));

        whenItemIsEquipped(ItemType.EldritchMarshNecklace);

        assertThat(tower.getItemChance()).isEqualTo(0.7f);
        assertThat(tower.getItemQuality()).isEqualTo(1.1f);
    }
}