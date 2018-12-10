package com.example.marek.minesweeper;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class GameEngine {

    private static GameEngine instance;

    private GameEngine(){ }

    private Context context;

    private Cell[][] MinesweeperGrid = new Cell[WIDTH][HEIGHT];

    private long startTime = 0;

    private TextView timerView;

    private Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            TextView timerView = ((MainActivity)context).findViewById(R.id.timer);
            timerView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };



    private static final int BOMB_NUMBER = 10;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 8;

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
                    MinesweeperGrid[x][y] = new Cell( context, x,y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }


        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable,0);
    }

    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / WIDTH;

        return MinesweeperGrid[x][y];

    }

    private Cell getCellAt(int x, int y) {

        return MinesweeperGrid[x][y];

    }


    public void click(int x, int y) {



        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x,y).isClicked())
        {
            Switch s = ((MainActivity)context).findViewById(R.id.switcher);

            if(s.isChecked())
            {
                flag(x,y);
            }

            else
            {
                getCellAt(x, y).setClicked();

                if (getCellAt(x, y).getValue() == 0)
                {

                    for (int xt = -1; xt <= 1; xt++)
                    {
                        for (int yt = -1; yt <= 1; yt++)
                        {
                            if (xt != yt)
                            {
                                click(x + xt, y + yt);
                            }
                        }
                    }
                }

                if (getCellAt(x, y).isBomb() && !getCellAt(x,y).isFlagged())
                {
                    onGameLost();
                }
                else
                {
                    checkEnd();
                }
            }
        }



    }

    private boolean checkEnd()
    {
        int bombFound = 0;
        int notRevealed = WIDTH * HEIGHT - BOMB_NUMBER;

        for (int x = 0; x<WIDTH;x++)
        {
            for (int y = 0; y<HEIGHT;y++)
            {
                if(getCellAt(x,y).isRevealed())
                {
                    notRevealed--;
                }

                if(getCellAt(x,y).isFlagged())
                {
                    bombFound++;
                }
            }
        }
        if(bombFound == BOMB_NUMBER && notRevealed == 0 )
        {
            onGameWon();
        }


        TextView mineCount = ((MainActivity)context).findViewById(R.id.minesCounter);
        int bombs = BOMB_NUMBER - bombFound;
        mineCount.setText("Mines: " + bombs);



        return true;
    }

    private void onGameWon()
    {
        timerHandler.removeCallbacks(timerRunnable);

        Toast.makeText(context,"Game won",Toast.LENGTH_SHORT).show();

        for (int x = 0; x<WIDTH;x++)
        {
            for (int y = 0; y<HEIGHT;y++)
            {
                getCellAt(x,y).setRevealed(true);
            }
        }

    }

    private void onGameLost()
    {

        timerHandler.removeCallbacks(timerRunnable);

        Toast.makeText(context,"Game lost",Toast.LENGTH_SHORT).show();
        MediaPlayer explosion = MediaPlayer.create(context,R.raw.explosion);
        explosion.setVolume(1, 1);
        explosion.start();
        for (int x = 0; x<WIDTH;x++)
        {
            for (int y = 0; y<HEIGHT;y++)
            {
                getCellAt(x,y).bombReveal();

            }
        }
    }

    public void flag(int x, int y)
    {
            boolean isFlagged = getCellAt(x, y).isFlagged();
            getCellAt(x, y).setFlagged(!isFlagged);
            getCellAt(x, y).invalidate();

    }
}
