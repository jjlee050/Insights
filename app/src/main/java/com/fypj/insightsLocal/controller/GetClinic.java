package com.fypj.insightsLocal.controller;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.options.Settings;
import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.insightsLocal.ui_logic.ViewClinicActivity;
import com.fypj.insightsLocal.util.ClinicAdapter;

import com.fypj.mymodule.api.insightsClinics.InsightsClinics;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 13/10/2014.
 */
public class GetClinic extends AsyncTask<Void, Void, List<Clinic>> implements Settings {
  private static InsightsClinics myApiService = null;
    private Activity context;
    private ListView lvNearestClinic;
    private SwipeRefreshLayout swipeView;
    final ArrayList<Clinic> ClinicArrList = new ArrayList<Clinic>();
    private ProgressDialog dialog;
    private String name;


    public GetClinic(Context context, String name, SwipeRefreshLayout swipeView){
        this.context = (Activity) context;
        this.lvNearestClinic = lvNearestClinic;
        this.name = name;
        this.swipeView = swipeView;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Retrieving Clinics", "Please wait...", true);
    }

    @Override
    protected List<Clinic> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsClinicsAPI();
        }
        try {
            return myApiService.listClinics().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Clinic> result) {
        if (result != null) {
            for (Clinic e : result) {
                if (e.getCategory().equals("Medical")) {
                    ClinicArrList.add(e);
                }
            }
            boolean recordExists = false;
            ArrayList<Clinic> foundClinics = new ArrayList<Clinic>();

            for(int i=0;i<ClinicArrList.size();i++){
                if((ClinicArrList.get(i).getName().equals(name))){
                    recordExists = true;
                    foundClinics.add(ClinicArrList.get(i));
                }
            }

           /* if(recordExists){
                final ClinicSQLController controller = new ClinicSQLController(context);
                for(int i=0;i<foundClinics.size();i++) {
                   Clinic clinic = controller.getClinic(id, foundClinics.get(i).getClinicID());
                    if(clinic.getClinicID() == 0){
                        controller.insertClinic(foundClinics.get(i));
                    }
                }
            }*/
        }
        dialog.dismiss();
          /*  ClinicAdapter adapter = new ClinicAdapter(context, android.R.id.text1, ClinicArrList);
            lvNearestClinic.setAdapter(adapter);

            lvNearestClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(context, ViewClinicActivity.class);
                    intent.putExtra("clinicID", ClinicArrList.get(position).getClinicID());
                    intent.putExtra("name", ClinicArrList.get(position).getName());
                    intent.putExtra("address", ClinicArrList.get(position).getAddress());
                    intent.putExtra("operatingHours", ClinicArrList.get(position).getOperatingHours());
                    intent.putExtra("contactNo", ClinicArrList.get(position).getContactNo());
                    intent.putExtra("category", ClinicArrList.get(position).getCategory());

                    context.startActivity(intent);


                }
            });
            ClinicSQLController controller = new ClinicSQLController(context);
            if (controller.getAllClinic().size() > 0) {
                controller.deleteAllClinic();
            }
            for (int i = 0; i < ClinicArrList.size(); i++) {
                controller.insertClinic(ClinicArrList.get(i));
            }

            dialog.dismiss();
            swipeView.setRefreshing(false);
        }*/
    }




    private void errorOnExecuting(){
        this.cancel(true);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
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
        swipeView.setRefreshing(false);
    }
}