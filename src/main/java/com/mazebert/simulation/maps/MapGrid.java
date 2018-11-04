package com.mazebert.simulation.maps;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.math.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MapGrid {
    private final int width;
    private final int height;
    private final Tile[] tiles;

    public MapGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width * height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTile(int x, int y, Tile tile) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            tiles[x + y * width] = tile;
        }
    }

    public void setTiles(int x, int y, int w, int h, Tile tile) {
        for (int iy = y; iy < y + h; ++iy) {
            for (int ix = x; ix < x + w; ++ix) {
                setTile(ix, iy, tile);
            }
        }
    }

    public Tile getTile(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height ? tiles[x + y * width] : null;
    }

    public Path findPath(int srcX, int srcY, int dstX, int dstY) {
        int srcIndex = srcX + srcY * width;
        int dstIndex = dstX + dstY * width;

        if (srcIndex == dstIndex) {
            return new Path(srcX, srcY);
        }

        if( srcIndex < 0 || srcIndex >= tiles.length || dstIndex < 0 || dstIndex >= tiles.length ) {
            return new Path(srcX, srcY);
        }

        List<Point> result = new ArrayList<>();

        int[] grid = new int[tiles.length];
        int i = 0;
        for (Tile tile : tiles) {
            if (tile.type.walkable) { // TODO pass cost function
                grid[i] = -1;
            } else {
                grid[i] = Integer.MAX_VALUE;
            }
            ++i;
        }

        // flood fill grid with distances
        Stack<Integer> stack = new Stack<>();
        int[] adjacentIndices = new int[9];
        int[] adjacentValues = new int[9];
        stack.push(srcIndex);
        grid[srcIndex] = 0; // zero cost at start position

        while( !stack.isEmpty() ) {
            int index = stack.pop();
            int cost = grid[index];

            int px = index % width;
            int py = index / width;

            for( int iy = -1; iy <= 1; ++iy )
            {
                for( int ix = -1; ix <= 1; ++ix )
                {
                    int j = ix + 1 + 3 * ( iy + 1 );
                    if( !( ix == 0 && iy == 0 ) &&
                            px + ix >= 0 && px + ix < width &&
                            py + iy >= 0 && py + iy < height ) // ignore center point and disallow mirroring
                    {
                        i = index + ix + iy * width;
                        adjacentIndices[j] = i;
                        adjacentValues[j] = grid[i];
                    }
                    else
                    {
                        adjacentIndices[j] = -1;
                        adjacentValues[j] = -1;
                    }
                }
            }

            if( adjacentValues[1] == Integer.MAX_VALUE && adjacentValues[3] == Integer.MAX_VALUE )
            {
                // not able to proceed top-left diagonal
                adjacentIndices[0] = -1;
            }
            else if( adjacentValues[1] == Integer.MAX_VALUE && adjacentValues[5] == Integer.MAX_VALUE )
            {
                // not able to proceed top-right diagonal
                adjacentIndices[2] = -1;
            }
            else if( adjacentValues[7] == Integer.MAX_VALUE && adjacentValues[3] == Integer.MAX_VALUE )
            {
                // not able to proceed bottom-left diagonal
                adjacentIndices[6] = -1;
            }
            else if( adjacentValues[7] == Integer.MAX_VALUE && adjacentValues[5] == Integer.MAX_VALUE )
            {
                // not able to proceed bottom-right diagonal
                adjacentIndices[8] = -1;
            }

            // fill grid with movement cost
            for (int adjacentIndex : adjacentIndices) {
                if ((i = adjacentIndex) != -1 && grid[i] == -1) {
                    grid[i] = cost + 1;
                    stack.push(i);
                }
            }
        }


        // start backward search
        int bestStraightIndex = -1;
        if( grid[dstIndex] != -1 && grid[dstIndex] != Integer.MAX_VALUE )
        {
            int index = dstIndex;
            result.add(new Point(index % width, index / width));

            do
            {
                // search for best adjacent cell
                int px = index % width;
                int py = index / width;
                for( int iy = -1; iy <= 1; ++iy )
                {
                    for( int ix = -1; ix <= 1; ++ix )
                    {
                        int j = ix + 1 + 3 * ( iy + 1 );
                        if( !( ix == 0 && iy == 0 ) &&
                                px + ix >= 0 && px + ix < width &&
                                py + iy >= 0 && py + iy < height ) // ignore center point and disallow mirroring
                        {
                            i = index + ix + iy * width;
                            adjacentIndices[j] = i;
                            adjacentValues[j] = grid[i];
                        }
                        else
                        {
                            adjacentIndices[j] = -1;
                            adjacentValues[j] = Integer.MAX_VALUE;
                        }
                    }
                }

                if( adjacentValues[1] == Integer.MAX_VALUE && adjacentValues[3] == Integer.MAX_VALUE )
                {
                    // not able to proceed top-left diagonal
                    adjacentValues[0] = Integer.MAX_VALUE;
                }
                else if( adjacentValues[1] == Integer.MAX_VALUE && adjacentValues[5] == Integer.MAX_VALUE )
                {
                    // not able to proceed top-right diagonal
                    adjacentValues[2] = Integer.MAX_VALUE;
                }
                else if( adjacentValues[7] == Integer.MAX_VALUE && adjacentValues[3] == Integer.MAX_VALUE )
                {
                    // not able to proceed bottom-left diagonal
                    adjacentValues[6] = Integer.MAX_VALUE;
                }
                else if( adjacentValues[7] == Integer.MAX_VALUE && adjacentValues[5] == Integer.MAX_VALUE )
                {
                    // not able to proceed bottom-right diagonal
                    adjacentValues[8] = Integer.MAX_VALUE;
                }

                bestStraightIndex = 1;
                i = 3; if( adjacentValues[i] < adjacentValues[bestStraightIndex] ) bestStraightIndex = i;
                i = 5; if( adjacentValues[i] < adjacentValues[bestStraightIndex] ) bestStraightIndex = i;
                i = 7; if( adjacentValues[i] < adjacentValues[bestStraightIndex] ) bestStraightIndex = i;
                bestStraightIndex = adjacentIndices[bestStraightIndex];

                if( bestStraightIndex == -1 )
                {
                    // no path could be found!!
                    return new Path(srcX, srcY);
                } else {
                    index = bestStraightIndex;
                }

                result.add(new Point(index % width, index / width));

            } while( index != srcIndex );
        }

        Collections.reverse(result);


        // optimize path for smooth animation
        if( result.size() > 2 )
        {
            // always add first point
            List<Point> smoothResult = new ArrayList<>();
            int dx0 = result.get(1).x - result.get(0).x;
            int dy0 = result.get(1).y - result.get(0).y;
            smoothResult.add(result.get(0));

            // smooth points in between
            for( i = 1; i < result.size() - 1; ++i )
            {
                int dx1 = result.get(i + 1).x - result.get(i).x;
                int dy1 = result.get(i + 1).y - result.get(i).y;

                if (dx0 != dx1 || dy0 != dy1) {
                    // direction changed, push point
                    dx0 = dx1;
                    dy0 = dy1;
                    smoothResult.add( result.get(i) );
                }

            }

            // always add last point
            smoothResult.add( result.get(result.size() - 1));
            result = smoothResult;
        }

        return new Path(result);
    }

}
