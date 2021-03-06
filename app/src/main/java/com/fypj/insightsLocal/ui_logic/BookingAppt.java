package com.fypj.insightsLocal.ui_logic;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.os.Bundle;
import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.CreateAppointment;
import com.fypj.insightsLocal.sqlite_controller.AppointmentSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.mymodule.api.insightsAppointment.model.Appointment;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.fypj.mymodule.api.insightsUser.model.User;

import android.view.View.OnClickListener;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BookingAppt extends ActionBarActivity implements OnClickListener {

    //static int SUBMIT_DIALOG = 1;

    private Button ib;
    private Calendar cal;
    private TextView clinicname;

    private EditText name;
    private EditText NRIC;
    private EditText ContactNo;


    private int year;
    private int month;
    private int day;
    private int hours;
    private int minutes;
    private String Currentdate;
    private String CurrentTime;


    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID=1;




    private Button pickTime;
    private Appointment appointment = new Appointment();
    private Clinic clinic = new Clinic();
    private Long clinicID;
    private String address;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appt);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ib = (Button) findViewById(R.id.Date);
        pickTime = (Button) findViewById(R.id.pickTime);
        clinicname = (TextView) findViewById(R.id.clinicname);





        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null) {
            String name = savedInstanceState.getString("name");
            clinicID = savedInstanceState.getLong("clinicID");
            address = savedInstanceState.getString("address");

            clinicname.setText("Clinic Name : " + name);


        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        hours = cal.get(Calendar.HOUR_OF_DAY);
        minutes = cal.get(Calendar.MINUTE);
        Currentdate = dateFormat.format(cal.getTime());
        CurrentTime = timeFormat.format(cal.getTime());




       getData();

        //ed = (EditText) findViewById(R.id.editDate);
        //editTime = (EditText) findViewById(R.id.editTime);


        ib.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Show the TimePickerDialog
                showDialog(DATE_DIALOG_ID);
            }
        });

        pickTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Show the TimePickerDialog
                showDialog(TIME_DIALOG_ID);
                //updateTime(hours, minutes);
            }
        });

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

            appointment.setClinicID(clinicID);
            appointment.setNric(NRIC.getText().toString());
            appointment.setDate(ib.getText().toString());
            appointment.setTime(pickTime.getText().toString());
            appointment.set("ClinicName", clinicname.getText().toString());
            appointment.set("Address", address);


            AppointmentSQLController controller = new AppointmentSQLController(this);
            ArrayList<Appointment> AppointmentArrList = controller.getAppointmentByClinic(clinicID);

           boolean isValid = true;

            for (int i = 0;i<AppointmentArrList.size();i++) {
                //System.out.println("Client side: " + AppointmentArrList.get(position).getTime());
                //controller.getAppointment()
                if (AppointmentArrList.get(i).getNric().equals(appointment.getNric()) && AppointmentArrList.get(i).getDate().equals(appointment.getDate()) && AppointmentArrList.get(i).getTime().equals(appointment.getTime())) {

                    isValid = false;
                    System.out.println(isValid);

                    break;

                }
            }

                if (isValid == true) {
                    Appointment newAppoint = appointment;
                    newAppoint.setAppointmentID(Long.parseLong(String.valueOf(AppointmentArrList.size() + 1)));
                    controller.insertAppointment(newAppoint);
                    newAppoint.setAppointmentID(null);
                    CreateAppointment createAppointment = new CreateAppointment(this, appointment);
                    createAppointment.execute();
                }

                else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(BookingAppt.this);
                    builder1.setMessage("Booking Appointment Failed. Please select another date and time. ");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();

                        }
                    });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }


            }



        return super.onOptionsItemSelected(item);
    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID: {
                return new DatePickerDialog(this, datePickerFromListener, year, month, day);
            }

            case TIME_DIALOG_ID: {

                return new TimePickerDialog(this, mTimeSetListener, hours, minutes, false);
            }
        }
        return null;

    }

    private DatePickerDialog.OnDateSetListener datePickerFromListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {


        String date = (selectedDay+"/"+(selectedMonth + 1)+"/"+selectedYear);
            if (date.compareTo(Currentdate)> 0) {
                ib.setText(date);
            }
            else if (date.compareTo(Currentdate) == 0)
            {
                ib.setText(date);
            }
            else {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(BookingAppt.this);
                builder2.setMessage("Sorry , Please select another date!");
                builder2.setCancelable(true);
                builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });
                AlertDialog alert10 = builder2.create();
                alert10.show();
            }


}
};

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                // the callback received when the user "sets" the TimePickerDialog in the dialog
                public void onTimeSet(TimePicker view, int hourSelected, int minuteSelected) {

                    hours = hourSelected;
                    minutes = minuteSelected;

                    updateTime(hours, minutes);

                   // editTime.setText (new StringBuilder().append(pad(hourSelected)).append(":").append(pad(minuteSelected)));
                }
            };

    @Override
    public void onClick(View view) {

    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private void updateTime(int hours, int minute) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (minute < 10)
            minutes = "0" + minute;
        else
            minutes = String.valueOf(minute);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
        pickTime.setText(aTime);
      /* if (aTime.compareTo(CurrentTime)>0){

        pickTime.setText(aTime);
    }

    else {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(BookingAppt.this);
        builder3.setMessage("Sorry , Please book 20 minutes earlier upon your appointment!");
        builder3.setCancelable(true);
        builder3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();

            }
        });
        AlertDialog alert9 = builder3.create();
        alert9.show();
    }*/

    }

    public void getData(){
        SharedPreferences sharedPref= getSharedPreferences("insightsPreferences", 0);
        String nric = sharedPref.getString("nric", "");
        if(!nric.equals("")){
            UserSQLController controller = new UserSQLController(this);
            User user = controller.getUser(nric);


             name = (EditText) findViewById(R.id.name);
             NRIC = (EditText) findViewById(R.id.nric);
             ContactNo = (EditText) findViewById(R.id.ContactNo);

            name.setText(user.getName());
            NRIC.setText(user.getNric());

            ContactNo.setText(user.getContactNo());





        }
    }



}
