package com.itpos.itposcheckin.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itpos.itposcheckin.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kylealanr on 9/4/14.
 */
public class TimeClock extends DefaultFragment {

    public Button checkInButton;
    public boolean isCheckIn = true;
    public String empName, timeIn, timeOut;

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
                isCheckIn = !isCheckIn;

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
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return String.valueOf(fmt.format(calNow.getTime()));
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

        SharedPreferences preferences = getActivity().getSharedPreferences("com.itpos.itposcheckin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("employee_name", employeeName);
        editor.apply();
        empName = employeeName;
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
            timeIn = getDate();

            SharedPreferences preferences = getActivity().getSharedPreferences("com.itpos.itposcheckin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("clock_in", timeIn);
            editor.apply();

            lastEvent.setText("On The Clock Since: \n" + timeIn);

            Toast toast = Toast.makeText(getActivity(), "Checking in..." + timeIn, Toast.LENGTH_SHORT);
            toast.show();

            checkInButton.setText("Clock Out");
        } else {
            TextView lastEvent = (TextView)getActivity().findViewById(R.id.last_timesheet_event);
            timeOut = getDate();

            SharedPreferences preferences = getActivity().getSharedPreferences("com.itpos.itposcheckin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("clock_out", timeOut);
            editor.apply();

            lastEvent.setText("Off The Clock Since: \n" + timeOut);

            Toast toast = Toast.makeText(getActivity(), "Checking out..." + timeOut, Toast.LENGTH_SHORT);
            toast.show();

            //empName = "Test Employee";
            //timeIn = "2014-11-07T03:22:00Z";
            //timeOut = "2014-11-07T04:22:00Z";

            new AsyncPostTest().execute(preferences.getString("employee_name", "Test"), preferences.getString("clock_in", "1979-11-07T01:00:00Z"), preferences.getString("clock_out", "1979-11-07T05:00:00Z"));

            checkInButton.setText("Clock In");
        }
    }

    //TODO onClick make HttpPost for clock in, then use same id for HttpPut request after

    /*
    The three types used by an asynchronous task are the following:

    Params, the type of the parameters sent to the task upon execution.
    Progress, the type of the progress units published during the background computation.
    Result, the type of the result of the background computation.

     */
    class AsyncPostTest extends AsyncTask <String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            //PostTest();
            String temp1, temp2, temp3 = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://104.236.41.123:8000/timesheet/");
            HttpResponse response;
            JSONObject json = new JSONObject();
            temp1 = params[0];
            temp2 = params[1];
            temp3 = params[2];
            try {
                json.put("employee_name", temp1);
                json.put("clock_in", temp2);
                json.put("clock_out", temp3);
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(se);
                response = httpClient.execute(httpPost);
                Log.d("POST Test", "Trying to POST");
            } catch (ClientProtocolException e) {
                Log.e("CLIENT PROTOCOL EXCEPTION", "Error in HttpPost");
            } catch (IOException e) {
                Log.e("IOException", "Error in HttpPost");
            } catch (JSONException e) {
                Log.e("JSONException", "Error in HttpPost");
            }
            return null;
        }
    }
}
