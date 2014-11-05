package com.fypj.insightsLocal.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.CalendarContract;
import android.widget.Toast;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.ui_logic.MainPageActivity;
import com.fypj.insightsLocal.ui_logic.NearestClinicActivity;
import com.fypj.mymodule.api.insightsAppointment.InsightsAppointment;
import com.fypj.mymodule.api.insightsAppointment.model.Appointment;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
//import com.fypj.mymodule.api.insightsEvent.model.Event;

/**
 * Created by L33525 on 14/10/2014.
 */
public class CreateAppointment extends AsyncTask<Void, Void, Boolean> {
    private static InsightsAppointment myApiService = null;
    private Activity context;
    private Appointment appointment;
    private ProgressDialog dialog;

    private Calendar cal;
    private int day;
    private int month;
    private int year;


    public CreateAppointment(Activity context, Appointment appointment){
        this.context = context;
        this.appointment = appointment;
    }

    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Progressing..", "Please wait...", true);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsAppointmentAPI();
        }
        try {
            String text = "";
            Appointment createdAppointment = myApiService.insertAppointment(appointment).execute();
            if(createdAppointment != null) {
                System.out.println("Created: " + appointment.getAppointmentID());




                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            errorOnExecuting();
            e.printStackTrace();
            return false;
        }
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    protected void onPostExecute(Boolean result){
        dialog.dismiss();

        /*Intent calIntent = new Intent (Intent.ACTION_INSERT);
        calIntent.setData (CalendarContract.Events.CONTENT_URI);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE,"Appointment for " + appointment.get("ClinicName"));
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Address : " + appointment.get("Address"));
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Appointment Timing : " + appointment.getTime());



        String[] splitDate = appointment.getDate().split("/");
        int day = Integer.parseInt(splitDate[0]);
        int month = (Integer.parseInt(splitDate[1]) - 1);
        int year = Integer.parseInt(splitDate[2]);


        GregorianCalendar calDate = new GregorianCalendar(year ,month, day);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());

        calIntent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        calIntent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        context.startActivity(calIntent);*/

        try {
            final ContentValues event = new ContentValues();
            event.put(CalendarContract.Events.CALENDAR_ID, 1);
            event.put(CalendarContract.Events.TITLE, "Appointment for " + appointment.get("ClinicName"));
            event.put(CalendarContract.Events.DESCRIPTION, "Appointment Timing : " + appointment.getTime());
            event.put(CalendarContract.Events.EVENT_LOCATION,  String.valueOf(appointment.get("Address")));


            String[] splitDate = appointment.getDate().split("/");
            int day = Integer.parseInt(splitDate[0]);
            int month = (Integer.parseInt(splitDate[1]) - 1);
            int year = Integer.parseInt(splitDate[2]);


            GregorianCalendar calDate = new GregorianCalendar(year, month, day);
            event.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
            event.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis());
            event.put(CalendarContract.Events.ALL_DAY, 0);
            event.put(CalendarContract.Events.HAS_ALARM, 1);

            String timeZone = TimeZone.getDefault().getID();
            event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

            Uri baseUri = null;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/calendars");
            }

            context.getContentResolver().insert(Uri.parse(getCalendarUriBase(context) + "events"), event);
            context.finish();

            Intent i = new Intent(context, NearestClinicActivity.class);
            context.startActivity(i);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        Toast.makeText(context, "Booking of Appointment Successfully", Toast.LENGTH_LONG).show();

    }

    private void errorOnExecuting(){
        this.cancel(true);
    }


    private String getCalendarUriBase(Activity activity) {

        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = activity.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = activity.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }

}
