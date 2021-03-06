/*
Alexandra Sazhin
Random Deck App
The first Fragment, that is why it is on "Top"
It is the starting fragment and is the default.
Allows user to input parameters to generate a
decklist.
 */

package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment implements View.OnClickListener{
    EditText _deckName;
    EditText _colors;
    EditText _set;
    TextView _waitText;
    Button _genButton;

    String name;
    String colors;
    String set;

    TextView topText;
    TextView colorsText;
    TextView setText;

    ArrayList<String> deck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View set up
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        _deckName = (EditText) v.findViewById(R.id.deckNameEdit);
        _colors = (EditText) v.findViewById(R.id.colorEdit);
        _set = (EditText) v.findViewById(R.id.setEdit);
        _waitText = (TextView) v.findViewById(R.id.waitText);
        _genButton = (Button) v.findViewById(R.id.genBtn);
        _genButton.setOnClickListener(this);

        deck = new ArrayList<String>();

        topText = (TextView) v.findViewById(R.id.topText);
        colorsText = (TextView) v.findViewById(R.id.colorText);
        setText = (TextView) v.findViewById(R.id.setText);

        //change font
        if(MainActivity._fontID == 0){
            topText.setTypeface(Typeface.DEFAULT);
            colorsText.setTypeface(Typeface.DEFAULT);
            setText.setTypeface(Typeface.DEFAULT);
        }
        if(MainActivity._fontID == 1){
            topText.setTypeface(Typeface.SERIF);
            colorsText.setTypeface(Typeface.SERIF);
            setText.setTypeface(Typeface.SERIF);
        }
        if(MainActivity._fontID == 2){
            topText.setTypeface(Typeface.MONOSPACE);
            colorsText.setTypeface(Typeface.MONOSPACE);
            setText.setTypeface(Typeface.MONOSPACE);
        }



        return v;

    }

    //generates the deck using Asynk, then gives it to the deck Fragment to display
    public void onClick (View view){
        if(view.getId() == R.id.genBtn){
            _waitText.setText("Please wait. Generating...");

            //get String
            name = _deckName.getText().toString();
            colors = _colors.getText().toString();
            set = _set.getText().toString();

            //generate
            GenAsync gen = new GenAsync();
            gen.execute(name, set, colors);

            //put stuff into deck

            try{
                ArrayList<String> deckAsync = gen.get();
                for(int i = 0; i<deckAsync.size(); i++){
                    deck.add(deckAsync.get(i));
                }

                for(int i = 0; i < deck.size(); i++) {
                    Log.d("deck", deck.get(i));
                }

                //switch fragment
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment frag = new DeckListFragment();

                Bundle args = new Bundle();
                args.putStringArrayList("deck", deck);
                frag.setArguments(args);

                ft.replace(R.id.content_frame, frag, "visible_fragment");
                ft.addToBackStack(frag.toString());
                ft.commit();




            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e){
                e.printStackTrace();
            }




        }
    }

    public ArrayList<String> getDeck(){
        return deck;
    }





}
