package com.example.marek.minesweeper;

import android.content.Context;
import android.widget.Switch;

public class GameEngine {

    private static GameEngine instance;

    private GameEngine(){ }

    private Context context;

    private Cell[][] MinesweeperGrid = new Cell[WIDTH][HEIGHT];


    public static final int BOMB_NUMBER = 10;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    public static GameEngine getInstance(){
        if (instance == null)
        {
            instance = new GameEngine();
        }

        return instance;
    }

    public void createGrid(Context context){
        this.context = context;

        int[][] generatedGrid = Generator.generate(BOMB_NUMBER,WIDTH,HEIGHT);
        setGrid(context,generatedGrid);
    }

    private void setGrid( final Context context, final int[][] grid){
        for (int x = 0; x<WIDTH;x++)
        {
            for (int y=0; y<HEIGHT;y++)
            {
                if( MinesweeperGrid[x][y] == null ){
                    MinesweeperGrid[x][y] = new Cell( context, y * HEIGHT + x);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }


    }

    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / HEIGHT;

        return MinesweeperGrid[x][y];

    }

    public Cell getCellAt(int x, int y) {

        return MinesweeperGrid[x][y];

    }


    public void click(int x, int y) {
        Switch s = ((MainActivity)context).findViewById(R.id.switcher);


        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x,y).isClicked() && !s.isChecked())
        {
            getCellAt(x,y).setClicked();

            if ( getCellAt(x,y).getValue()==0)
            {
                for(int xt = -1;xt<=1;xt++)
                {
                    for(int yt = -1;yt<=1;yt++)
                    {
                        if(xt!=yt)
                        {
                            click(x+xt,y+yt);
                        }
                    }
                }
            }

            if (getCellAt(x,y).isBomb())
            {
                onGameLost();
            }
        }

        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x,y).isClicked() && s.isChecked())
        {
            flag(x,y);
        }

    }

    private void onGameLost() {
        // lost game
    }

    public void flag(int x, int y) {

        boolean isFlagged = getCellAt(x,y).isFlagged();
        getCellAt(x,y).setFlagged(!isFlagged);
        getCellAt(x,y).invalidate();

    }
}
