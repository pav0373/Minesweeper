package com.example.marek.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(GameEngine.PREFS, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            int id = prefs.getInt("id", 0); //0 is the default value.

            GameEngine.getInstance().setDifficulty(id);
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.restart:
                restart();
                return true;
            case R.id.obtiznost:
                obtiznost();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void restart() {

        SharedPreferences prefs = getSharedPreferences(GameEngine.PREFS, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);

        if (restoredText != null) {
            int id = prefs.getInt("id", 0); //0 is the default value.

            GameEngine.getInstance().setDifficulty(id);
        }

        GameEngine.getInstance().refresh();
        GridView mine = findViewById(R.id.minesweeperGridView);
        mine.invalidateViews();

    }

    private void obtiznost() {
        Intent difficultySetting = new Intent(MainActivity.this, DifficultyActivity.class);
        startActivityForResult(difficultySetting,100);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100)
        {
            long id=data.getLongExtra("Difficulty",0);
            Toast.makeText(this,"Obtiznost "+(int)(id+1),Toast.LENGTH_LONG).show();
            GameEngine.getInstance().setDifficulty((int)id);
            GameEngine.getInstance().refresh();
            GridView mine = findViewById(R.id.minesweeperGridView);
            mine.invalidateViews();

        }
    }


}
