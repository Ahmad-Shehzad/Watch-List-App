package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText entryName;
    Spinner category;

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
                goToRandom();
            }
        });

        //Programming the pick tv show button
        pickTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRandom();
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

                db.add(name, cat, 0); //need a method to find api id, set 0 for now
                entryName.setText("");
            }
        });

    }

    //Methods to navigate between activities
    public void goToView(){
        startActivity(new Intent(this, ViewList.class));
    }

    public void goToRandom(){
        startActivity(new Intent(this, RandomOutput.class));
    }
}