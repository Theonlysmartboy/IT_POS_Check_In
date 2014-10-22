package com.itpos.itposcheckin.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itpos.itposcheckin.R;

/**
 * Created by kylealanr on 9/4/14.
 */
public class TimeClock extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.time_clock, container, false);
        return view;
    }
    //TODO onClick make http request and get JSON
}
