package com.example.marek.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SaveActivity extends Activity {

    private int time;
    private int diff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);


        TextView timeText = findViewById(R.id.timeText);
        TextView diffText = findViewById(R.id.difficultyText);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            time = extras.getInt("time");
            diff = extras.getInt("diff");

            timeText.setText("Cas: " + time + " sekund");

            switch (diff) {
                case 0:
                    diffText.setText("Obtiznost: Lehka");
                    break;
                case 1:
                    diffText.setText("Obtiznost: Stredni");
                    break;
                case 2:
                    diffText.setText("Obtiznost: Tezka");
                    break;
                default:
                    diffText.setText("Obtiznost: Lehka");
                    break;
            }


        }

        Button button = findViewById(R.id.buttonSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Score save = new Score();
                EditText edit = findViewById(R.id.nameEdit);
                String name = edit.getText().toString();


                save.setName(name);
                save.setTime(time);
                save.setDifficulty(diff);

                AppDatabase.getAppDatabase(getApplicationContext()).scoreDao().insertAll(save);

                finish();

            }
        });


    }


}
