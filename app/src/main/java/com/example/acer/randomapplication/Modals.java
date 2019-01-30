package com.example.acer.randomapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.acer.randomapplication.R;

public class Modals {

    public static View getTitle(LayoutInflater inflater, String title){
        View v = inflater.inflate(R.layout.modals_title, null, false);
        TextView t = (TextView) v.findViewById(R.id.title);
        t.setText(title);
        return v;
    }

}
