package com.example.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.view.contentcapture.DataRemovalRequest;

import java.util.ArrayList;

public class DataBaseAdapter {
    DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DataBaseAdapter(Context context){
        this.dataBaseHelper = new DataBaseHelper(context.getApplicationContext());
    }

    public DataBaseAdapter Open(){
        database = dataBaseHelper.getReadableDatabase();
        return DataBaseAdapter.this;
    }

    public void Close(){
        dataBaseHelper.close();
    }

    public void insert(Profile profile){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_SNAME, profile.sName);
        cv.put(DataBaseHelper.COLUMN_NAME, profile.name);
        cv.put(DataBaseHelper.COLUMN_AGE, profile.age);
        cv.put(DataBaseHelper.COLUMN_IMAGE, profile.photo);

        database.insert(DataBaseHelper.TABLE_PROFILE, null, cv);
    }
    public void update(Profile profile){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_SNAME, profile.sName);
        cv.put(DataBaseHelper.COLUMN_NAME, profile.name);
        cv.put(DataBaseHelper.COLUMN_AGE, profile.age);
        cv.put(DataBaseHelper.COLUMN_IMAGE, profile.photo);
        database.update(DataBaseHelper.TABLE_PROFILE, cv, "_id =  ?", new String[]{String.valueOf(profile._id)});
    }
    public void delete(long id){
        database.delete(DataBaseHelper.TABLE_PROFILE, "_id = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getProfile(){
        String[] columns = new String[]{DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_SNAME, DataBaseHelper.COLUMN_NAME, DataBaseHelper.COLUMN_AGE, DataBaseHelper.COLUMN_IMAGE};
        return  database.query(DataBaseHelper.TABLE_PROFILE, columns, null, null, null, null, null);
    }

    public ArrayList<Profile> profile(){
        ArrayList<Profile> profiles = new ArrayList<>();

        Cursor cursor = getProfile();

        while (cursor.moveToNext()){
            Profile profile = new Profile(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_SNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AGE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_IMAGE))
            );
            profiles.add(profile);
        }

        return profiles;
    }

    public Profile getSingleProfile(long id){
        Cursor cursor = database.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_PROFILE + " WHERE " + DataBaseHelper.COLUMN_ID + " =?", new String[]{String.valueOf(id)});
        cursor.moveToNext();
        return new Profile(cursor.getInt(
                cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_SNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AGE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_IMAGE))
        );
    }

}
