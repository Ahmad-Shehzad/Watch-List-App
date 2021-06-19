package com.example.watchlistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Database";
    public static final String TABLE_NAME = "List";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_API = "API";
    public static final String COLUMN_POSTER = "Poster";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGERPRIMARYKEY, "+ COLUMN_TITLE + " TEXT, " + COLUMN_CATEGORY + " TEXT, " + COLUMN_API + " TEXT, " + COLUMN_POSTER + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Method for adding entries to the list
    public void add (String title, String category, int apiID, String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(COLUMN_ID, (entries() + 1));
        content.put(COLUMN_TITLE, title);
        content.put(COLUMN_CATEGORY, category);
        content.put(COLUMN_API, apiID);
        content.put(COLUMN_POSTER, path);

        db.insert(TABLE_NAME, null, content);

        db.close();
    }

    //Returns the number of entries in the list
    public int entries() {
        SQLiteDatabase db = this.getReadableDatabase();
        int num = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return num;
    }

    //delete entries
    public void delete (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] {Integer.toString(id)});
        db.close();
    }

    //Returns entry name
    public String getEntry(int id, String cat) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'" + " AND " + COLUMN_CATEGORY + " = '" + cat + "'", null);
        cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
    }

    //Returns poster path for an entry
    public String getPath(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        cursor.moveToFirst();

        return cursor.getString(cursor.getColumnIndex(COLUMN_POSTER));
    }

    //Method returns an array of all films or tv shows
    public ArrayList<String> getEntries(String cat) {
        ArrayList<String> entries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = '" + cat +"'", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            entries.add(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            cursor.moveToNext();
        }

        return entries;
    }

    //returns ids of entries from a certain category
    public ArrayList<Integer> getIds(String cat) {
        ArrayList<Integer> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CATEGORY + " = '" + cat + "'", null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            ids.add(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            cursor.moveToNext();
        }

        return ids;
    }
}


