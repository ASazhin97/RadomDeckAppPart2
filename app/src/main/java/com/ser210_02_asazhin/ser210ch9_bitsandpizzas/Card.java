/*
Alexandra Sazhin
Random Deck App
this class holds and receives 1 card
including its name, its colors, and its text
 */

package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;

/**
 * Created by alexa on 4/8/2018.
 */

public class Card {
    public String _name;
    public String _colors;
    public String _text;
    public int _id;

    public Card(String n, String c, String t){
        _name = n;
        _colors = c;
        _text = t;
    }

    public Card(){

    }


    public String getName(){
        return _name;
    }

    public String getColors(){
        return _colors;
    }

    public String getText(){
        return _text;
    }

    public void setName(String n){
        _name = n;
    }

    public void setColors(String c){
        _colors = c;
    }

    public void setText(String t){
        _text = t;
    }

    public void setId(int id){_id = id; }

    public int getID(){ return _id; }
}
