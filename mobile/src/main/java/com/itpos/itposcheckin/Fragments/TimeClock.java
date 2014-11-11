package com.itpos.itposcheckin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        final EditText pinBox = (EditText)view.findViewById(R.id.pin_box);

        checkInButton = (Button)view.findViewById(R.id.time_clock_button);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clockAuth(pinBox.getText().toString())) {
                    timeClock(isCheckIn);
                } else {
                    errorToast();
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

    public boolean clockAuth(String pin) {
        String[] pins = getResources().getStringArray(R.array.user_pins);
        for (int i = 0; i < pins.length; i++) {
            if (pin.equals(pins[i])) {
                changeEmployee(i);
                return true;
            }
        }
      return false;
    }

    public void changeEmployee(int position) {
        String employeeName = "";

        String[] names = getResources().getStringArray(R.array.employees);
        employeeName = names[position];

        TextView employeeText = (TextView) getActivity().findViewById(R.id.employee_text_view);
        employeeText.setText(employeeName);
    }

    public void errorToast() {
        TextView employeeText = (TextView) getActivity().findViewById(R.id.employee_text_view);
        employeeText.setText("Welcome, User!");

        TextView lastEvent = (TextView) getActivity().findViewById(R.id.last_timesheet_event);
        lastEvent.setText("Please contact the owner if you do not remember your pin");

        Toast toast = Toast.makeText(getActivity(), "Woops! Enter a valid pin" , Toast.LENGTH_SHORT);
        toast.show();
    }

    public void timeClock(boolean isCheckIn) {
        if (isCheckIn) {
            TextView lastEvent = (TextView) getActivity().findViewById(R.id.last_timesheet_event);
            lastEvent.setText("On The Clock Since: \n" + getDate());

            Toast toast = Toast.makeText(getActivity(), "Checking in..." + getDate(), Toast.LENGTH_SHORT);
            toast.show();

            isCheckIn = false;
            checkInButton.setText("Clock Out");
        } else {
            TextView lastEvent = (TextView)getActivity().findViewById(R.id.last_timesheet_event);
            lastEvent.setText("Off The Clock Since: \n" + getDate());

            Toast toast = Toast.makeText(getActivity(), "Checking out..." + getDate(), Toast.LENGTH_SHORT);
            toast.show();

            isCheckIn = true;
            checkInButton.setText("Clock In");
        }
    }

    //TODO onClick make HttpPost for clock in, then use same id for HttpPut request after

}
