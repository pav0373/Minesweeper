package com.example.marek.minesweeper;

import java.util.Random;

public class Generator {

    public static int[][] generate(int bombnumber, final int width, final int height)
    {
        Random r=new Random();

        int[][] grid = new int[width][height];

        for (int x = 0; x < width;x++)
        {
            grid[x] = new int[height];
        }

        while(bombnumber>0)
        {
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            if (grid[x][y]!=-1)
            {
                grid[x][y]=-1;
                bombnumber--;
            }
        }
        grid = calculateNeighbours(grid,width,height);

        return grid;
    }


    private static int[][] calculateNeighbours( int[][] grid, final int width, final int height)
    {
        for (int x = 0; x<width;x++)
        {
            for (int y = 0; y<height;y++){
                grid[x][y]= getNeighborNumber(grid,x,y,width,height);
            }
        }
        return grid;
    }

    private static int getNeighborNumber(final int grid[][],final int x, final int y, final int width, final int height)
    {
        if (grid[x][y]==-1)
        {
            return -1;
        }

        int count = 0;

        if (isMineAt(grid,x-1,y-1,width,height)) count++;
        if (isMineAt(grid,x-1,   y,width,height))   count++;
        if (isMineAt(grid,x-1,y+1,width,height)) count++;
        if (isMineAt(grid,   x,  y-1,width,height)) count++;
        if (isMineAt(grid,   x,  y+1,width,height)) count++;
        if (isMineAt(grid,x+1,y-1,width,height)) count++;
        if (isMineAt(grid,x+1,   y,width,height))   count++;
        if (isMineAt(grid,x+1,y+1,width,height)) count++;

        return count;
    }

    private static boolean isMineAt(final int[][] grid, final int x, final int y, final int width, final int height) {
        return x >= 0 && y >= 0 && x < width && y < height && grid[x][y] == -1;
    }
}
