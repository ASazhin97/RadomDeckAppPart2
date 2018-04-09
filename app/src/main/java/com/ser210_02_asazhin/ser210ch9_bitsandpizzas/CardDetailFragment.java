package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;


import android.app.ListFragment;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardDetailFragment extends ListFragment {
    ArrayList<String> card;
    DatabaseHandler db;
    Card c;

    public CardDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        db = new DatabaseHandler(getActivity());
        ArrayList<String> card = new ArrayList<String>();
        //card.add("hi");

        Bundle bundle = getArguments();
        int id = bundle.getInt("id");

        try {
            db.open();
            c = db.getCardAtId(id);
            db.close();
            Log.i("card", c.getName());

            card.add(c.getName());
            card.add(c.getColors());
            card.add(c.getText());


        } catch (java.sql.SQLException e){
            e.printStackTrace();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                card);
        setListAdapter(adapter);

        return v;
    }

    public Card getCard(){
        return c;
    }

}
