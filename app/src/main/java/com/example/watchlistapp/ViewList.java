package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewList extends AppCompatActivity {

    ListView list;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        db = new Database(this);
        list = findViewById(R.id.filmList);
        final Toast toast = Toast.makeText(this, "Entry Deleted", Toast.LENGTH_SHORT);

        final Handler handler = new Handler(); // used to add delay

        updateLists();

        list.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final int position = i;

               if (position > db.getEntries("Film").size() + 1) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            db.delete(db.getID(list.getItemAtPosition(position).toString(), "TV Show"));
                            updateLists();
                            toast.show();
                        }
                    }, 150);
                }

                else if (position <= db.getEntries("Film").size() && position != 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            db.delete(db.getID(list.getItemAtPosition(position).toString(), "Film"));
                            updateLists();
                            toast.show();
                        }
                    }, 150);
                }
            }
        });
    }

    private void updateLists() {
        ArrayList<String> films = db.getEntries("Film");
        ArrayList<String> tv = db.getEntries("TV Show");

        films.add(0, "Films");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, films);
        adapter.add("TV Shows");
        adapter.addAll(tv);

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setAdapter(adapter);
    }

}