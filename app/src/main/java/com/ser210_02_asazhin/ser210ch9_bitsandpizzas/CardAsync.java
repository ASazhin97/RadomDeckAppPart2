/*
Alexandra Sazhin
Random Deck App
This connects to the API, picks out a random card and displays it to the user.
When on this screen the user can choose to add this card to his favorites.
 */

package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alexa on 4/5/2018.
 */

public class CardAsync extends AsyncTask<String, Void, ArrayList<String>> {
    ArrayList<String> cardInfo;
    ArrayList<JSONObject> cards;

    String nameCard;
    String colorsCard;
    String textCard;


    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        //variable set up
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        cardInfo = new ArrayList<String>();

        try {

            //creating the connection
            //Log.e("name of card", "doing Async");
            URL url = new URL("https://community-deckbrew.p.mashape.com/mtg/cards?mashape-key=LpjuiLPka7mshcWdWoYQA4dxJpeZp1nLNkwjsnD19V2Y7lz6MO");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));

            String JSONSString = getJSonStringFromBuffer(reader);
            Log.i("RAW DATA", JSONSString);
            cards = new ArrayList<JSONObject>();

            //creating an array list of JSON objects
            try {
                JSONArray jsonArray = new JSONArray(JSONSString);

                int count = jsonArray.length();
                for (int i = 0; i < count; i++) {

                    cards.add(jsonArray.getJSONObject(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            //not what we have the Jason Array, get a random card to display
            try{

                int random = (int)(Math.random()*cards.size());
                JSONObject card = cards.get(random);
                String nameCard = card.getString("name");
                String colorsCard = card.getJSONArray("colors").toString();
                String textCard = card.getString("text");

                //Log.e("card Info", nameCard + " " + colorsCard);
                cardInfo.add(nameCard);
                cardInfo.add(colorsCard);
                cardInfo.add(textCard);

            } catch (JSONException e){
                e.printStackTrace();
                Log.e("error", "");
            }



        } catch (Exception e) {
            e.printStackTrace();


            //////////////////////////////////////////////////////////////////////////////////////
        } finally { //closing connection
            if (connection != null) connection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException io) {
                    Log.e("reader error", "Reader Closing Error");
                    return null;
                }
            }
        }


        return cardInfo;
    }


    //This code gets a JSON String from the Biffered reader
    //taken from class example
    private String getJSonStringFromBuffer(BufferedReader br) throws Exception {
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) {
            return null;
        }
        return buffer.toString();
    }


}
