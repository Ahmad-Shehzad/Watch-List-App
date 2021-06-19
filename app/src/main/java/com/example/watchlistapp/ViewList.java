package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewList extends AppCompatActivity {

    ListView list;
    ListView list2;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        //Temporary layout, ideally need to create custom one
        db = new Database(this);
        list = findViewById(R.id.filmList);
        list2 = findViewById(R.id.tvList);

        updateLists();

        list.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                db.delete(db.getID(list.getItemAtPosition(i).toString(), "Film"));
                updateLists();
                return false;
            }
        });

        list2.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                db.delete(db.getID(list2.getItemAtPosition(i).toString(), "TV Show"));
                updateLists();
                return false;
            }
        });

    }

    private void updateLists() {
        ArrayList<String> films = db.getEntries("Film");
        ArrayList<String> tv = db.getEntries("TV Show");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, films);
        ArrayAdapter<String> adpater2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, tv);

        list.setAdapter(adapter);
        list2.setAdapter(adpater2);
    }

}