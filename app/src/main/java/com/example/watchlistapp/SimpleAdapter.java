package com.example.watchlistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<String> mData;
    Database db;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final CheckedTextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.listEntry);
        }
    }

    public SimpleAdapter(Context context, ArrayList<String> data, Database database) {
        mContext = context;
        mData = data;
        db = database;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.title.setText(mData.get(position));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.title.setChecked(true);
                holder.title.setClickable(false);
                if (position < db.getEntries("Film").size()) {
                    db.delete(db.getID(holder.title.getText().toString(), "Film"));
                } else {
                    db.delete(db.getID(holder.title.getText().toString(), "TV Show"));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}