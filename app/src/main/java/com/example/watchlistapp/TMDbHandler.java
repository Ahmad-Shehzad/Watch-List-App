package com.example.watchlistapp;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class TMDbHandler extends MainActivity {

    String title;
    String cat;
    String cat2;
    String path;
    int id;

    public TMDbHandler(String name, String category){
        title = name;
        cat2 = category;
        if (category.equals("Film")){
            cat = "movie";
        }
        else {
            cat = "tv";
        }

        new genFilm().execute();
    }

    public class genFilm extends AsyncTask<Void, Void, Void> {
        String result;

        @Override
        public Void doInBackground(Void... voids) {
            String url = "https://api.themoviedb.org/3/search/" + cat;
            String key = "cc6cada9d5dfe3c0f069fc0472fc604f";
            URL requestURL;
            try {
                requestURL = new URL(url + "?api_key=" + key + "&query=" + title);
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
//                title = bestMatch.getString("title");
                id = bestMatch.getInt("id");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }
}
