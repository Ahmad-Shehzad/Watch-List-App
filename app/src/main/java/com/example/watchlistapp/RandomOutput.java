package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RandomOutput extends AppCompatActivity {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_output);

        genFilm();

        Button generateNew = findViewById(R.id.generateNew);
        generateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genFilm();
            }
        });

    }

    private void genFilm() {
        output = findViewById(R.id.output);
        Bundle bundle = getIntent().getExtras();
        String cat = bundle.getString("Category");

        Database db = new Database(this);
        ArrayList<Integer> ids = db.getIds(cat);

        int length = ids.size();
        int index = (int) (Math.random()*length);
        output.setText(db.getEntry(ids.get(index), cat));
    }
}