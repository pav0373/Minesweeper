package com.example.marek.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DifficultyActivity extends Activity {

    String[] difficultyArray = {"Lehka","Stredni","Tezka"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_difficulty,
                R.id.textView, this.difficultyArray);
        listView = findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter, View v, int position, long id){
                Intent intent = new Intent();
                intent.putExtra("Difficulty", id);
                setResult(100,intent);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }








}
