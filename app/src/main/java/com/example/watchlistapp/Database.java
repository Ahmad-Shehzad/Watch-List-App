package com.example.watchlistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Database";
    public static final String TABLE_NAME = "List";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_API = "API";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGERPRIMARYKEY, "+ COLUMN_TITLE + " TEXT, " + COLUMN_CATEGORY + " TEXT, " + COLUMN_API + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    //Method for adding entries to the list
    public void add (String title, String category, int apiID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(COLUMN_ID, entries() + 1);
        content.put(COLUMN_TITLE, title);
        content.put(COLUMN_CATEGORY, category);
        content.put(COLUMN_API, apiID);

        db.close();
    }

    //Returns the number of entries in the list
    public int entries() {
        SQLiteDatabase db = this.getReadableDatabase();
        int num = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return num;
    }

    //delete entries
    public void delete (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] {Integer.toString(id)});
        db.close();
    }//need to consider what happens to the ids when item deleted for random generation, may need renumbering system

}


