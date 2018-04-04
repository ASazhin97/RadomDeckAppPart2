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
 * Created by alexa on 3/28/2018.
 */

public class GenAsync extends AsyncTask<String, Void, ArrayList<String>> {
    ArrayList<String> deckList;
    ArrayList<JSONObject> cards;
    String set = "";
    String colors = "";
    String name = "";

    public ArrayList<String> doInBackground(String... param) {
        deckList = new ArrayList<>();
        name = param[0];
        set = param[1];
        colors = param[2];


        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            //creating the connection
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

            //now that we have our data it is time to create our deck

            //The name of the deck goes on top.
            deckList.add("Set: " + set);
            deckList.add("Deck Name: " + name);
            deckList.add("Colors: " + colors);
            deckList.add("DECK");


            //now get the set

            //Legacy 20 lands 20 cards
            //Commander 40 lands 60 cards
            //Modern 30 lands 30 cards
            int size = 20;
            int lands = 20;
            if (set.contains("Legacy")) {
                size = 40;
                lands = 20;
            }
            if (set.equals("Commander")) {
                size = 100;
                lands = 40;
            }
            if (set.equals("Modern")) {
                size = 60;
                lands = 30;
            }

            //get number of colors
            int colorCount = 0;
            if (colors.contains("black")) {
                colorCount++;
            }
            if (colors.contains("green")) {
                colorCount++;
            }
            if (colors.contains("white")) {
                colorCount++;
            }
            if (colors.contains("red")) {
                colorCount++;
            }
            if (colors.contains("blue")) {
                colorCount++;
            }

            //adding the lands to the deck
            int cardsPerLand = lands / colorCount;
            if (colors.contains("black")) {
                deckList.add(cardsPerLand + " Swamp");
            }
            if (colors.contains("green")) {
                deckList.add(cardsPerLand + " Forest");
            }
            if (colors.contains("white")) {
                deckList.add(cardsPerLand + " Plains");
            }
            if (colors.contains("red")) {
                deckList.add(cardsPerLand + " Mountain");
            }
            if (colors.contains("blue")) {
                deckList.add(cardsPerLand + " Island");
            }

            //adding the rest of the cards
            try {
                int cardsLeft = size - (cardsPerLand * colorCount);
                for (int i = 0; i < cardsLeft; i++) {
                    int random = (int) (Math.random() * cards.size());
                    if (cards.get(random).has("colors")) {
                        JSONArray c = cards.get(random).getJSONArray("colors");
                        Boolean goodCard = true;

                        //figure out if this card is good or not
                        for (int s = 0; s < c.length(); s++) {
                            if (!colors.contains(c.getString(s))) {
                                goodCard = false;
                            }
                        }

                        if (goodCard) {
                            Log.i("SUCCESS CARD", cards.get(i).getString("name"));
                            deckList.add(1 + " " + cards.get(i).getString("name"));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //catch and close connection
        } catch (Exception e) {
            e.printStackTrace();


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

        //returns the generated deck
        return deckList;
    }

    //not needed but is implemented
    public void onPostExceture() {

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