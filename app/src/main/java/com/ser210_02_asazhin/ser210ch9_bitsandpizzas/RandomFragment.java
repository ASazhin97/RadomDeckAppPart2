package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RandomFragment extends ListFragment {
    ArrayList<String> _card;
    ArrayList<String> _genCard;

    public RandomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _card = new ArrayList<String>();

        Bundle bundle = getArguments();
        _genCard = bundle.getStringArrayList("card");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                _genCard);
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
