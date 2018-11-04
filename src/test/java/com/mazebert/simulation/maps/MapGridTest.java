package com.mazebert.simulation.maps;

import com.mazebert.simulation.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapGridTest {
    private static final Tile walkable = new Tile(new TileType("1", 0, 0).walkable());
    private static final Tile blocked = new Tile(new TileType("1", 0, 0));

    private MapGrid grid;
    private int srcX;
    private int srcY;
    private int dstX;
    private int dstY;

    private Path path;

    @Test
    void smallestMap() {
        grid = new MapGrid(1, 1);

        path = grid.findPath(0, 0, 0, 0);

        thenPathIs(0, 0);
    }

    @Test
    void sourceOutOfGrid() {
        grid = new MapGrid(1, 1);

        path = grid.findPath(-2, -20, 0, 0);

        thenPathIs(-2, -20);
    }

    @Test
    void noObstacle() {
        givenGrid("sd");
        whenPathIsFound();
        thenPathIs("01");
    }

    @Test
    void obstacle() {
        givenGrid(
                "***",
                "s*d",
                "   "
        );

        whenPathIsFound();

        thenPathIs(
                "***",
                "0*3",
                "1 2"
        );
    }

    @Test
    void maze() {
        givenGrid(
                "  s*d ",
                " **** ",
                "   *  ",
                "**   *"
        );

        whenPathIsFound();

        thenPathIs(
                "1 0*98",
                " **** ",
                "2 3*67",
                "**4 5*"
        );
    }

    @Test
    void impossibleToReach() {
        givenGrid(
                "s      ",
                " ****  ",
                " *d *  ",
                " ****  "
        );

        whenPathIsFound();

        thenPathIs(0, 0); // simply stay at source
    }

    private void givenGrid(String... rows) {
        grid = new MapGrid(rows[0].length(), rows.length);
        for (int y = 0; y < grid.getHeight(); ++y) {
            for (int x = 0; x < grid.getWidth(); ++x) {
                char tile = rows[y].charAt(x);
                switch (tile) {
                    case 's':
                        grid.setTile(x, y, walkable);
                        srcX = x;
                        srcY = y;
                        break;
                    case 'd':
                        grid.setTile(x, y, walkable);
                        dstX = x;
                        dstY = y;
                        break;
                    case ' ':
                        grid.setTile(x, y, walkable);
                        break;
                    case '*':
                        grid.setTile(x, y, blocked);
                }
            }
        }
    }

    private void whenPathIsFound() {
        path = grid.findPath(srcX, srcY, dstX, dstY);
    }

    private void thenPathIs(float... coordinates) {
        assertThat(path.getPoints()).containsExactly(coordinates);
    }

    private void thenPathIs(String... rows) {
        int length = 0;
        for (int y = 0; y < grid.getHeight(); ++y) {
            for (int x = 0; x < grid.getWidth(); ++x) {
                int index = parsePathIndex(rows[y].charAt(x));
                length = Math.max(length, index + 1);
            }
        }

        float[] expected = new float[2 * length];
        for (int y = 0; y < grid.getHeight(); ++y) {
            for (int x = 0; x < grid.getWidth(); ++x) {
                int index = parsePathIndex(rows[y].charAt(x));
                if (index >= 0) {
                    expected[index * 2] = x;
                    expected[index * 2 + 1] = y;
                }
            }
        }

        thenPathIs(expected);
    }

    private int parsePathIndex(char c) {
        try {
            return Integer.parseInt("" + c);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}