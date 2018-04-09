/*
Alexandra Sazhin
Random Deck App
A list that shows all of the favorites in the databse
When click on a card, details show up
 */

package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends ListFragment {

    ArrayList<Card> cards;
    DatabaseHandler db;
    ArrayList<String> card_names;

    public FavoritesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = super.onCreateView(inflater, container, savedInstanceState);
        db = new DatabaseHandler(getActivity());
        card_names = new ArrayList<String>();

        try {
            db.open();
            cards = db.getAllCards();
            db.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

        //get card names
        for(int i=0; i<cards.size(); i++){
            card_names.add(cards.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                card_names);
        setListAdapter(adapter);


        return v;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Log.i("clicked", "c");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        android.app.Fragment frag = new CardDetailFragment();

        try {
            db.open();
            Bundle args = new Bundle();
            int cardID = cards.get(position).getID();

        args.putInt("id", cardID);
        frag.setArguments(args);

        ft.replace(R.id.content_frame, frag, "visible_fragment");
        ft.addToBackStack(frag.toString());
        ft.commit();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
