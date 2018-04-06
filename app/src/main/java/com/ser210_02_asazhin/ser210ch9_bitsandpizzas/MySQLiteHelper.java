package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexa on 4/5/2018.
 */

public class MySQLiteHelper extends SQLiteOpenHelper{
    public static final String TABLE_COMMENTS = "deck";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String DECK_LIST = "deckList";

    public static final String DATABASE_NAME = "favorites_db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase db;

    //create sql db
    private static final String DATABASE_CREATE = "CREATE TABLE "
            +TABLE_COMMENTS + "(" + COLUMN_ID + " integer primary key autoincrement, "
            +COLUMN_NAME + " text not null default ' ');";


    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getReadableDatabase();
        Log.e("create", "Constructor of SQL Helper");

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("create", "Created The database");
        db.execSQL(DATABASE_CREATE);
    }

    public void insertDeck(SQLiteDatabase db, String name, ArrayList<String> list){
        ContentValues deckValues = new ContentValues();
        deckValues.put(COLUMN_NAME, name);
        //deckValues.put(DECK_LIST, list);
        db.insert(DATABASE_NAME, null, deckValues);
        Log.e("return", "put deck in");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}
