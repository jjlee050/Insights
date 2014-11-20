package com.fypj.insightsLocal.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.options.Settings;
import com.fypj.insightsLocal.sqlite_controller.AppointmentSQLController;
import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.mymodule.api.insightsAppointment.InsightsAppointment;
import com.fypj.mymodule.api.insightsAppointment.model.Appointment;
import com.fypj.mymodule.api.insightsClinics.InsightsClinics;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 13/10/2014.
 */
public class GetAppointment extends AsyncTask<Void, Void, List<Appointment>> implements Settings {
    private static InsightsAppointment myApiService = null;
    private Context context;
    private ListView lvNearestClinic;
    final ArrayList<Appointment> AppointmentArrList = new ArrayList<Appointment>();
    private ProgressDialog dialog;

    public GetAppointment(Context context){
        this.context = (Context) context;
    }
  /*  public GetClinic(Context context, ListView lvNearestClinic, SwipeRefreshLayout swipeView){
        this.context = (Activity) context;
        this.lvNearestClinic = lvNearestClinic;
        this.swipeView = swipeView;
    }*/


    @Override
    protected List<Appointment> doInBackground(Void... voids) {
        if(myApiService == null) { // Only do this once
            myApiService = AppConstants.getInsightsAppointmentAPI();
        }
        try {
            return myApiService.listAppointment().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting();
            return Collections.EMPTY_LIST;
        }
    }
    @Override
    protected void onPostExecute(List<Appointment> result) {
        if (result != null) {
            for (Appointment e : result) {
                AppointmentArrList.add(e);
                }
            }

            AppointmentSQLController controller = new AppointmentSQLController(context);
            if (controller.getAllAppointment().size() > 0) {
                controller.deleteAllAppointment();
            }
            for (int i = 0; i < AppointmentArrList.size(); i++) {
                Appointment appointment = controller.getAppointment(AppointmentArrList.get(i).getAppointmentID());
                if(appointment.getAppointmentID().equals(Long.parseLong("0"))) {
                    controller.insertAppointment(AppointmentArrList.get(i));
                }

            }
         //   // swipeView.setRefreshing(false);
        }

    private void errorOnExecuting(){
        this.cancel(true);
      /*  new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Error in retrieving event ");
                builder.setMessage("Unable to retrieve event. Please try again.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub
                        cancel(true);
                    }
                });
                builder.create().show();
            }
        });
        swipeView.setRefreshing(false);*/
    }
}