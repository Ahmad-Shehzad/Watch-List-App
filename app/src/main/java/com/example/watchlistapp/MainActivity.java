package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText entryName;
    Spinner category;
    String path;
    int id;
    Database db;
    String name;
    String cat;
    String cat2;
    Toast added;
    Toast err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding objects
        db = new Database(this);

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

        added = Toast.makeText(this, "Entry Added", Toast.LENGTH_SHORT);
        err = Toast.makeText(this, "Error: Entry Cannot Be Found", Toast.LENGTH_SHORT);

        //Programming add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = entryName.getText().toString(); //get input data and convert to string to store in database
                cat = category.getSelectedItem().toString();

                if (cat.equals("Film")) { //for API search
                    cat2 = "movie";
                }
                else {
                    cat2 = "tv";
                }

                new genInfo().execute(); //calls API to get data

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

    //API connection and data retrieval
    public class genInfo extends AsyncTask<Void, Void, Void> {
        String result;

        @Override
        public Void doInBackground(Void... voids) {
            String url = "https://api.themoviedb.org/3/search/" + cat2;
            String key = "cc6cada9d5dfe3c0f069fc0472fc604f";
            URL requestURL;

            try {
                requestURL = new URL(url + "?api_key=" + key + "&query=" + name);
                InputStreamReader in = new InputStreamReader(requestURL.openStream());
                BufferedReader bufferedReader = new BufferedReader(in);
                String stringBuffer;
                String string = "";

                while ((stringBuffer = bufferedReader.readLine()) != null){
                    string = String.format("%s%s", string, stringBuffer);
                }

                bufferedReader.close();

                result = string;

            } catch (IOException e){
                e.printStackTrace();
                result = e.toString();
            }
            return null;
        }
        @Override
        public void onPostExecute(Void aVoid) {
            try {
                JSONObject searchResults = new JSONObject(result);
                JSONArray results = searchResults.getJSONArray("results");
                JSONObject bestMatch = (JSONObject) results.get(0);

                path = bestMatch.getString("poster_path");
                id = bestMatch.getInt("id");

                db.add(name,cat,id, path);
                added.show();

            } catch (JSONException e) {
                e.printStackTrace();
                err.show();
            }
            super.onPostExecute(aVoid);
        }
    }
}