package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by alexa on 4/8/2018.
 */

public class DatabaseHandler {
    private MySQLiteHelper dbhelper;
    private SQLiteDatabase database;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_COLORS,MySQLiteHelper.COLUMN_TEXT};
    public DatabaseHandler(Context context) {
        this.dbhelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        this.database = dbhelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void close() {
        this.database.close();
    }
    public void addCard(String name, String colors, String text){
        name = (name!=null)? name : "";
        colors = (colors!=null)? colors : "";
        text = (text!=null)? text : "";
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME,name);
        values.put(MySQLiteHelper.COLUMN_COLORS,colors);
        values.put(MySQLiteHelper.COLUMN_TEXT,text);
        if(this.database==null){
            Log.e("No database","THERE IS NO DATABASE");
        }
        long id = database.insert(MySQLiteHelper.TABLE_COMMENTS,null,values);
        //  Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,allColumns,MySQLiteHelper.COLUMN_ID + " = ", new String[]{String.valueOf(id)},null,null,null);
        Log.i("ID",String.valueOf(id));
        //cursor.moveToFirst();
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> cards= new ArrayList<Card>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Card s = cursorToCard(cursor);
            cards.add(s);
            cursor.moveToNext();
        }
        return cards;
    }
    //ID, CITY,STATE, COUNTRY
    private Card cursorToCard(Cursor cursor) {
        Card s = new Card();
        s.setName(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME)));
        s.setColors(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_COLORS)));
        s.setText(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TEXT)));
        s.setId(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        return s;
    }

    public Card getCardAtId(int id){
        int Rid = id;
        Card c = new Card();
        Cursor cur = database.query(MySQLiteHelper.TABLE_COMMENTS,allColumns,null,null,null,null,null);
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            if (cur.getInt(cur.getColumnIndex("_id")) == Rid) {
                String name = cur.getString(cur.getColumnIndex(MySQLiteHelper.COLUMN_NAME));
                String colors = cur.getString(cur.getColumnIndex(MySQLiteHelper.COLUMN_COLORS));
                String text = cur.getString(cur.getColumnIndex(MySQLiteHelper.COLUMN_TEXT));

                c.setName(name);
                c.setColors(colors);
                c.setText(text);

            }
            cur.moveToNext();
        }

        return c;

    }


    public void removeCard(String name, String colors, String text){
        name = (name!=null)? name : "";
        colors = (colors!=null)? colors : "";
        text = (text!=null)? text : "";
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME,name);
        values.put(MySQLiteHelper.COLUMN_COLORS,colors);
        values.put(MySQLiteHelper.COLUMN_TEXT,text);
        if(this.database==null){
            Log.e("No database","THERE IS NO DATABASE");
        }
        long id = database.delete(MySQLiteHelper.TABLE_COMMENTS,MySQLiteHelper.COLUMN_NAME+"='"+name+"'",null);
    }
}