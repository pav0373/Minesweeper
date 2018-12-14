package com.example.marek.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class GameEngine {

    private static GameEngine instance;

    private GameEngine(){ }

    private Context context;

    private Cell[][] MinesweeperGrid;

    private long startTime = 0;

    private TextView timerView;

    private int sec;
    private int diff = 0;

    public static final String PREFS= "DifficultyPrefs";
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {


                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
            sec = seconds;
                int minutes = seconds / 60;
                seconds = seconds % 60;

                TextView timerView = ((MainActivity) context).findViewById(R.id.timer);
                timerView.setText(String.format("%d:%02d", minutes, seconds));

                timerHandler.postDelayed(this, 500);

        }
    };



    private static int BOMB_NUMBER = 10;
    public static int WIDTH = 10;
    public static int HEIGHT = 8;

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
        MinesweeperGrid = new Cell[WIDTH][HEIGHT];
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




            }
        }

        checkEnd();

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


        Intent saveScore = new Intent(context, SaveActivity.class);

        saveScore.putExtra("time", sec);
        saveScore.putExtra("diff", diff);
        context.startActivity(saveScore);

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


    public void setDifficulty(int id)
    {
        switch(id){
            case 0:
                HEIGHT=10;BOMB_NUMBER=10;
                break;

            case 1:
                HEIGHT=12;BOMB_NUMBER=20;
                break;
            case 2:
                HEIGHT=14;BOMB_NUMBER=30;
                break;
            default:
                HEIGHT=10;BOMB_NUMBER=10;
                id=0;
                break;


        }
        diff = id;

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, MODE_PRIVATE).edit();
        editor.putInt("id", id);
        editor.apply();

        TextView mineCount = ((MainActivity)context).findViewById(R.id.minesCounter);
        int bombs = BOMB_NUMBER;
        mineCount.setText("Mines: " + bombs);

    }

    public void refresh()
    {
        SharedPreferences prefs = context.getSharedPreferences(GameEngine.PREFS, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            int id = prefs.getInt("id", 0); //0 is the default value.

            GameEngine.getInstance().setDifficulty(id);
        }

        for (int x = 0; x<MinesweeperGrid.length;x++)
        {
            for (int y=0; y<MinesweeperGrid[x].length;y++)
            {
                    MinesweeperGrid[x][y] = null;
            }
        }

        int[][] generatedGrid = Generator.generate(BOMB_NUMBER,WIDTH,HEIGHT);
        setGrid(context,generatedGrid);


    }



}
