package com.itpos.itposcheckin.Fragments;

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
import java.io.UnsupportedEncodingException;
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
            lastEvent.setText("On The Clock Since: \n" + timeIn);

            Toast toast = Toast.makeText(getActivity(), "Checking in..." + timeIn, Toast.LENGTH_SHORT);
            toast.show();

            checkInButton.setText("Clock Out");
        } else {
            TextView lastEvent = (TextView)getActivity().findViewById(R.id.last_timesheet_event);
            timeOut = getDate();
            lastEvent.setText("Off The Clock Since: \n" + timeOut);

            Toast toast = Toast.makeText(getActivity(), "Checking out..." + timeOut, Toast.LENGTH_SHORT);
            toast.show();

            JSONObject json = new JSONObject();
            empName = "Kyle Riedemann";
            timeIn = "2014-11-07T03:22:00Z";
            timeOut = "2014-11-07T04:22:00Z";
            try {
                json.put("employee_name", empName);
                json.put("clock_in", timeIn);
                json.put("clock_out", timeOut);
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (JSONException e) {
                Log.e("JSONException", "Error in HttpPost");
            } catch (UnsupportedEncodingException e) {
                Log.e("UnsupportedEncodingException", "Error in HttpPost");
            }
            new AsyncPostTest().execute();

            checkInButton.setText("Clock In");
        }
    }

    //TODO onClick make HttpPost for clock in, then use same id for HttpPut request after
    //private class MyTask extends AsyncTask<Void, Void, Void> { ... }
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
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://104.236.41.123:8000/timesheet/");
            HttpResponse response;
            JSONObject json = new JSONObject();
            //empName = "Kyle Riedemann";
            //timeIn = "2014-11-07T03:22:00Z";
            //timeOut = "2014-11-07T04:22:00Z";
            try {
                json.put("employee_name", empName);
                json.put("clock_in", timeIn);
                json.put("clock_out", timeOut);
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

    public void PostTest() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://104.236.41.123:8000/timesheet/");
        JSONObject json = new JSONObject();
        HttpResponse response;
        empName = "Kyle Riedemann";
        timeIn = "2014-11-07T03:22:00Z";
        timeOut = "2014-11-07T04:22:00Z";
        try {
            json.put("employee_name", empName);
            json.put("clock_in", timeIn);
            json.put("clock_out", timeOut);
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
    }
}
