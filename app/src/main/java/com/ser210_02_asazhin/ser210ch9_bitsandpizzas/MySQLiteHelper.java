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
    public static final String TABLE_COMMENTS = "deck";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CARD = "deckList";

    public static final String DATABASE_NAME = "favorites_db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase db;

    //create sql db
    private String DATABASE_CREATE = "CREATE TABLE "
            + DATABASE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement, " // we use default so that if null/empty strings get added, they automatically change to something easily readable.
            + COLUMN_NAME + " text not null default ' ', "
            + COLUMN_CARD + " text not null default ' ');";

    String query = "CREATE TABLE "+DATABASE_NAME+" ("+COLUMN_ID+" INTEGER PRIMARY KEY, "+COLUMN_NAME+" varchar(20),"+COLUMN_CARD+" varchar(20))";

    public MySQLiteHelper(Context context){
        super(context, "testDB", null, DATABASE_VERSION);
        Log.e("create", "Constructor of SQL Helper");

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("create", "Created The database");
        db.execSQL(query);
    }

    public void insertDeck(String name, String card){
        Log.e("method", "insertDeck");
        String query = "INSERT INTO favorites_db values(null, ?, ?)";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query, new String[]{name,card});
        db.close();
    }

    public String getFavName(int id){
        Log.e("method", "getFavname");
        String nameFav = "Favorite name goes here";
        SQLiteDatabase db = this.getWritableDatabase();
        String queary2 = "SELECT name FROM favorites_db WHERE _id = ?";
        Cursor c = db.rawQuery(queary2, new String[]{Integer.toString(id)});
        db.close();
        return nameFav;
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
