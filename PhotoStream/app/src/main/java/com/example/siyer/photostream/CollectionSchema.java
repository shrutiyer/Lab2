package com.example.siyer.photostream;

import android.provider.BaseColumns;

/**
 * Created by siyer on 9/30/2015.
 * Schema for the database
 */
public class CollectionSchema {
    public CollectionSchema(){}

    public abstract class CollTable implements BaseColumns {
        public static final String TABLE_NAME = "colltable";
        public static final String URL_COLUMN= "url";
        public static final String URL_ID= "_id";

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + CollTable.TABLE_NAME +
                " (" + CollTable.URL_ID + " INTEGER PRIMARY KEY," + CollTable.URL_COLUMN + " TEXT )";

        public static final String SQL_DELETE_ENTRIES = "DELETE TABLE IF EXISTS " + CollTable.TABLE_NAME;
    }
}
