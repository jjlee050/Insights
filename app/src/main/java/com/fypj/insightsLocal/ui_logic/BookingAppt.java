package com.fypj.insightsLocal.ui_logic;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

import java.util.Calendar;


public class BookingAppt extends ActionBarActivity {

    static int SUBMIT_DIALOG = 1;

    Button btnDate, btnTime;

    private int yearFrom;
    private int monthFrom;
    private int dayFrom;

    static final int DATE_DIALOG_ID = 999;
    static final int DATE_DIALOG_ID_1 = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appt);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnDate = (Button) findViewById(R.id.btn_date);
        btnTime = (Button) findViewById(R.id.btn_time);



        Calendar calendarFrom = Calendar.getInstance();
        dayFrom = calendarFrom.get(Calendar.DAY_OF_MONTH);
        monthFrom = calendarFrom.get(Calendar.MONTH);
        yearFrom = calendarFrom.get(Calendar.YEAR);


       // btnDate.setText(new StringBuilder().append(dayFrom).append("/").append(monthFrom + 1).append("/").append(yearFrom).append(" "));


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.booking_appt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.Submit) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(BookingAppt.this);
            builder1.setMessage("Booking of Appointment Successful ");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(BookingAppt.this, NearestClinicActivity.class);
                    startActivity(intent);
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();


        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        //Intent intent = null;
        try {
            switch (view.getId()) {
                case R.id.btn_date:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_booking_appt, container, false);
            return rootView;
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date

                return new DatePickerDialog(this, datePickerFromListener, yearFrom,
                        monthFrom, dayFrom);
        }


        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerFromListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            yearFrom = selectedYear;
            monthFrom = selectedMonth;
            dayFrom = selectedDay;

            // set selected date into textview
            btnDate.setText(new StringBuilder().append(dayFrom).append("/")
                    .append(monthFrom + 1).append("/").append(yearFrom).append(" "));
        }
    };

}
