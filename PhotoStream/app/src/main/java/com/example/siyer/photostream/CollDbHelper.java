package com.example.siyer.photostream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by siyer on 9/30/2015.
 */
import com.example.siyer.photostream.CollectionSchema.CollTable;

import java.util.ArrayList;

public class CollDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "FeedReader.db";

    public CollDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CollTable.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CollTable.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void writeDatabase(String input){
        SQLiteDatabase writeDBase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CollTable.URL_COLUMN, input);

        writeDBase.insert(CollTable.TABLE_NAME, null, values);
        writeDBase.close();
    }

    public ArrayList<String> readDatabase(){

        SQLiteDatabase readDBase = getReadableDatabase();
        ArrayList<String> collectionUrl = new ArrayList<>();
        String[] projection = {
                CollTable.URL_ID,
                CollTable.URL_COLUMN};

        Cursor cursor = readDBase.query(CollTable.TABLE_NAME, projection,                               // The columns to return
                null,null,null,null,null
        );

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                collectionUrl.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        Log.d("getAllURLs", collectionUrl.toString());

        return collectionUrl;
    }

    public void deleteDatabase(String url){
        SQLiteDatabase deleteDBase = getWritableDatabase();

        String selection = CollTable.URL_COLUMN + " LIKE ? ";
        String[] selectionArgs = {String.valueOf(url)};

        deleteDBase.delete(CollTable.TABLE_NAME,selection,selectionArgs);

        deleteDBase.close();
    }

}