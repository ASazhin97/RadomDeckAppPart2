package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
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
public class DeckListFragment extends ListFragment {
    ArrayList<String> _deck;
    ArrayList<String> _genDeck;

    public DeckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _deck = new ArrayList<String>();

        Bundle bundle = getArguments();
        _genDeck = bundle.getStringArrayList("deck");


        for (int i = 0; i < _genDeck.size(); i++) {
            _deck.add(_genDeck.get(i));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                _deck);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
