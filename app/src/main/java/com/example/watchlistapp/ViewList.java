package com.example.watchlistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

public class ViewList extends AppCompatActivity {

    Database db;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        db = new Database(this);
        updateLists();

    }

    public void updateLists() {
        ArrayList<String> films = db.getEntries("Film");
        int numFilm = films.size();
        ArrayList<String> tv = db.getEntries("TV Show");

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));


        RecyclerView.Adapter mAdapter;
        films.addAll(tv);
        mAdapter = new SimpleAdapter(this, films, db);


        //Sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"Films"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(numFilm,"TV Shows"));

        //Add adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(this,R.layout.section,R.id.sectionText,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        mRecyclerView.setAdapter(mSectionedAdapter);


    }



}