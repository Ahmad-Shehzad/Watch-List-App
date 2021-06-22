package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RandomOutput extends AppCompatActivity {

    TextView output;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_output);

        newEntry();

        Button generateNew = findViewById(R.id.generateNew);
        generateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newEntry();
            }
        });

    }

    //sets poster and text
    private void newEntry() {
        output = findViewById(R.id.output);
        poster = findViewById(R.id.poster);
        Bundle bundle = getIntent().getExtras();
        String cat = bundle.getString("Category");

        Database db = new Database(this);
        ArrayList<Integer> ids = db.getIds(cat);

        int length = ids.size();
        int index = (int) (Math.random()*length);
        int id = ids.get(index);

        String path = db.getPath(id);

        Picasso.with(RandomOutput.this).load("https://image.tmdb.org/t/p/w500"+path).fit().into(poster);
        output.setText(db.getEntry(id, cat));
    }
}