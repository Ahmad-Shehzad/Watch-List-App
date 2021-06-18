package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class RandomOutput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_output);

        TextView output = findViewById(R.id.output);
        Bundle bundle = getIntent().getExtras();
        String cat = bundle.getString("Category");

        Database db = new Database(this);
        ArrayList<Integer> ids = db.getIds(cat);

        int length = ids.size();
        int index = (int) (1 + Math.random()*length);
        output.setText(db.getEntry(index, cat));


    }
}