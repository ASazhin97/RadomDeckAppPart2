package com.ser210_02_asazhin.ser210ch9_bitsandpizzas;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View v;
    Spinner backSpinner;
    Spinner fontSpinner;
    String[] backColors = {"White", "Blue", "Yellow"};
    String[] fonts = {"Default", "Serif", "Monospace"};
    TextView fontText;
    TextView backText;
    FrameLayout frame;
    TextView text;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        // Variables
        backSpinner = (Spinner) v.findViewById(R.id.backColorSpinner);
        fontSpinner = (Spinner) v.findViewById(R.id.fontColorSpinner);

        fontText = (TextView) v.findViewById(R.id.fontText);
        backText = (TextView) v.findViewById(R.id.backText);

        //Spinners
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, backColors);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        backSpinner.setAdapter(adapter1);
        backSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, fonts);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        fontSpinner.setAdapter(adapter2);
        fontSpinner.setOnItemSelectedListener(this);

        //other variables
        frame = getActivity().findViewById(R.id.content_frame);
        text = (TextView)getActivity().findViewById(R.id.topText);

        return v;
    }


    //deals with spinner clicking
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("clicked", "spinner");
        Spinner spinner = (Spinner) parent;
        //changes background color
        if(spinner.getId() == backSpinner.getId()){
            switch(position){
                case 0:
                    frame.setBackgroundColor(Color.WHITE);
                    break;
                case 1:
                    frame.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    frame.setBackgroundColor(Color.YELLOW);
            }


        }

        //chaning the font in progress PROGRESS
        if(spinner.getId() == fontSpinner.getId()){
            //change fot
            switch (position){
                case 0:
                    fontText.setTypeface(Typeface.DEFAULT);
                    text.setTypeface(Typeface.DEFAULT);

                    break;
                case 1:
                    fontText.setTypeface(Typeface.SERIF);
                    break;
                case 2:
                    fontText.setTypeface(Typeface.MONOSPACE);
                    break;
            }

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
