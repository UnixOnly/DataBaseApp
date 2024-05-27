package com.example.databaseapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "profileDb.db";
    private static final int SHEMA = 1;
    public static final String TABLE_PROFILE = "profile";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SNAME = "sName";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public  static final String COLUMN_IMAGE = "profileImage";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SHEMA);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PROFILE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_SNAME + " TEXT , " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INTEGER, " + COLUMN_IMAGE + " INTEGER "+ ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }
}
