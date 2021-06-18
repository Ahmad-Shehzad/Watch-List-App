package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        //Temporary layout, ideally need to create custom one
        final Database db = new Database(this);
        ListView list = findViewById(R.id.list);
        ArrayList<String> films = db.getEntries("Film");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,films);
        list.setAdapter(adapter);
}
}