package com.example.githmidiaryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Sql_Lite_DB_Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "My_Diaries.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "diary_entries_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "_title";
    private static final String COLUMN_IMAGE = "_image";
    private static final String COLUMN_DATE = "_date";
    private static final String COLUMN_ENTRY = "_entry";


    public Sql_Lite_DB_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating table in the DB
        String query = " CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_ENTRY + " TEXT); ";
        db.execSQL(query); // Executing create table query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void add_diary_data(String title, String image, String date, String entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_IMAGE, image);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_ENTRY, entry);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Log.e("DB Error", "Failed to add diary entry"); // Log the error for debugging
        } else {
            Log.e("DB fine", " added diary entry"); // This might cause crash if on background thread
        }
    }

    Cursor read_all_data() {
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void delete_selected_data(String selected_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{selected_id});

        if (result == -1) {
            Log.e("DB Error", "Failed to add diary entry"); // Log the error for debugging
        } else {
            Log.e("DB fine", "diary entry deleted"); // This might cause crash if on background thread
        }

    }


}
