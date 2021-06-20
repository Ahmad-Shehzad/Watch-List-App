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
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        //Temporary layout, ideally need to create custom one
        db = new Database(this);
        list = findViewById(R.id.filmList);

        updateLists();

        list.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                System.out.println(db.getEntries("Film").size());
                if (i > db.getEntries("Film").size() + 1) {
                    db.delete(db.getID(list.getItemAtPosition(i).toString(), "TV Show"));
                    updateLists();
                    return false;
                }
                else if (i <= db.getEntries("Film").size() && i != 0) {
                    db.delete(db.getID(list.getItemAtPosition(i).toString(), "Film"));
                    updateLists();
                    return false;
                }
                else {
                    return false;
                }
            }
        });
    }

    private void updateLists() {
        ArrayList<String> films = db.getEntries("Film");
        ArrayList<String> tv = db.getEntries("TV Show");

        films.add(0, "Films");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, films);
        adapter.add("TV Shows");
        adapter.addAll(tv);

        list.setAdapter(adapter);
    }

}