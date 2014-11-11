package com.itpos.itposcheckin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itpos.itposcheckin.R;

import java.util.Calendar;

/**
 * Created by kylealanr on 9/4/14.
 */
public class TimeClock extends DefaultFragment {

    public Button checkInButton;
    public boolean isCheckIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.time_clock, container, false);

        Spinner spinner = (Spinner)view.findViewById(R.id.employee_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.employees, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        checkInButton = (Button)view.findViewById(R.id.time_clock_button);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckIn) {
                    TextView lastEvent = (TextView)view.findViewById(R.id.last_timesheet_event);
                    lastEvent.setText("On The Clock Since: \n" + getDate());

                    Toast toast = Toast.makeText(getActivity(), "Checking in..." + getDate(), Toast.LENGTH_SHORT);
                    toast.show();

                    isCheckIn = false;
                    checkInButton.setText("Clock Out");
                } else {
                    TextView lastEvent = (TextView)view.findViewById(R.id.last_timesheet_event);
                    lastEvent.setText("Off The Clock Since: \n" + getDate());

                    Toast toast = Toast.makeText(getActivity(), "Checking out..." + getDate(), Toast.LENGTH_SHORT);
                    toast.show();

                    isCheckIn = true;
                    checkInButton.setText("Clock In");
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //return the current datetime as a string to show the user
    public String getDate() {
        Calendar calNow = Calendar.getInstance();
        String formattedDate = String.valueOf(calNow.getTime());
        return formattedDate;
    }

    //TODO onClick make HttpPost for clock in, then use same id for HttpPut request after

}
