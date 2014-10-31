package com.fypj.insightsLocal.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.mymodule.api.insightsAppointment.InsightsAppointment;
import com.fypj.mymodule.api.insightsAppointment.model.Appointment;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;

import java.util.List;
//import com.fypj.mymodule.api.insightsEvent.model.Event;

/**
 * Created by L33525 on 14/10/2014.
 */
public class CreateAppointment extends AsyncTask<Void, Void, Boolean> {
    private static InsightsAppointment myApiService = null;
    private Context context;
    private Appointment appointment;
    private ProgressDialog dialog;

    public CreateAppointment(Context context, Appointment appointment){
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
    protected void onPostExecute(List<Appointment> result){
        dialog.dismiss();
    }

    private void errorOnExecuting(){
        this.cancel(true);
    }
}
