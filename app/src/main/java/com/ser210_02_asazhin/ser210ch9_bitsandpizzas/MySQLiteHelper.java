/*
Alexandra Sazhin
Random Deck App
SQL helper to create databse and do querys. Is only
later intereacted by the DatabseHandler
 */

package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexa on 4/5/2018.
 */

public class MySQLiteHelper extends SQLiteOpenHelper{
    public static String COLUMN_ID = "_id";
    public static String COLUMN_NAME = "_name";
    public static String COLUMN_COLORS = "_colors";
    public static String COLUMN_TEXT = "_text";
    public static String TABLE_COMMENTS = "_cards";

    public static final String DATABASE_NAME = "_favCardsDb";
    public static final int DATABASE_VERSION = 1;
    SQLiteDatabase database;


    private static final String CREATE = "create table "
            + TABLE_COMMENTS + "(" + COLUMN_ID + " integer primary key autoincrement, " // we use default so that if null/empty strings get added, they automatically change to something easily readable.
            + COLUMN_NAME + " text not null default ' ', "
            + COLUMN_COLORS + " text not null default ' ', "
            + COLUMN_TEXT + " text not null default ' ');";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
        //database = this.getWritableDatabase();
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
