package com.example.marek.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreActivity extends Activity {

    private ArrayList<Score> scoreList;
    private ArrayList<String> stringList;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoreList = new ArrayList<Score>();
        stringList = new ArrayList<String>();
        scoreList.addAll(AppDatabase.getAppDatabase(getApplicationContext()).scoreDao().getAll());


        if (scoreList.isEmpty()) {
            finish();
        }

        for (Score element : scoreList) {
            String s = element.getName() + " : " + element.getTime() + "s ";
            switch (element.getDifficulty()) {
                case 0:
                    s = s + "Lehka";
                    break;
                case 1:
                    s = s + "Stredni";
                    break;
                case 2:
                    s = s + "Tezka";
                    break;
                default:
                    s = s + "Lehka";
                    break;


            }
            stringList.add(s);
        }

        lv = findViewById(R.id.score_list);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                stringList);


        lv.setAdapter(arrayAdapter);
    }
}
