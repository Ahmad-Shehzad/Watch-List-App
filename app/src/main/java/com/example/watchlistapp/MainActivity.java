package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText entryName;
    Spinner category;
    String path;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding objects
        final Database db = new Database(this);
        Button view = findViewById(R.id.view);
        Button pickMovie = findViewById(R.id.movie);
        Button pickTV = findViewById(R.id.tv);
        Button add = findViewById(R.id.add);

        //Programming the view list button
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToView();
            }
        });

        //Programming the pick movie button
        pickMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRandom("Film");
            }
        });

        //Programming the pick tv show button
        pickTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRandom("TV Show");
            }
        });

        //Adding input fields
        entryName = findViewById(R.id.entryName);
        category = findViewById(R.id.category);

        //Adding options to the spinner
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Film");
        categories.add("TV Show");

        //Creating and adding adapter to spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);

        //Programming add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = entryName.getText().toString(); //get input data and convert to string to store in database
                String cat = category.getSelectedItem().toString();

                TMDbHandler tmdb = new TMDbHandler(name, cat);

                db.add(name, cat, id, path);
                entryName.setText("");
            }
        });

    }

    //Methods to navigate between activities
    public void goToView(){
        startActivity(new Intent(this, ViewList.class));
    }

    public void goToRandom(String category){
        Database db = new Database(this);
        int length = db.getIds(category).size();
        Toast toast = Toast.makeText(this, "No entries in " + category, Toast.LENGTH_SHORT);

        if (length == 0 ){
            toast.show();
        }
        else {
            Intent intent = new Intent(this, RandomOutput.class);
            intent.putExtra("Category", category);
            startActivity(intent);
        }
    }
}