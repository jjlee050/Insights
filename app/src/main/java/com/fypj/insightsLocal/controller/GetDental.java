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
import com.fypj.insightsLocal.util.DentalAdapter;
import com.fypj.mymodule.api.insightsClinics.InsightsClinics;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 13/10/2014.
 */
public class GetDental extends AsyncTask<Void, Void, List<Clinic>> implements Settings {
  private static InsightsClinics myApiService = null;
    private Context context;
    private ListView lvNearestDental;
    private SwipeRefreshLayout swipeView;
    final ArrayList<Clinic> DentalArrList = new ArrayList<Clinic>();
    private ProgressDialog dialog;


    /*public GetDental(Context context, ListView lvNearestDental, SwipeRefreshLayout swipeView){
        this.context = (Activity) context;
        this.lvNearestDental = lvNearestDental;
        this.swipeView = swipeView;
    }*/
    public GetDental(Context context){
        this.context = (Context) context;
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
                if (e.getCategory().equals("Dental")) {
                    DentalArrList.add(e);
                }

                /*DentalAdapter adapter = new DentalAdapter(context, android.R.id.text1,DentalArrList);
                lvNearestDental.setAdapter(adapter);

                lvNearestDental.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(context, ViewClinicActivity.class);
                        intent.putExtra("clinicID", DentalArrList.get(position).getClinicID());
                        intent.putExtra("name", DentalArrList.get(position).getName());
                        intent.putExtra("address", DentalArrList.get(position).getAddress());
                        intent.putExtra("operatingHours", DentalArrList.get(position).getOperatingHours());
                        intent.putExtra("contactNo", DentalArrList.get(position).getContactNo());
                        intent.putExtra("category", DentalArrList.get(position).getCategory());

                        context.startActivity(intent);


                    }
                });*/

                ClinicSQLController controller = new ClinicSQLController(context);
                if (controller.getAllClinic().size() > 0) {
                    controller.deleteAllClinic("Dental");
                }


                for (int i = 0; i < DentalArrList.size(); i++) {
                    Clinic clinic = controller.getClinic(DentalArrList.get(i).getClinicID());
                    if(clinic.getClinicID().equals(Long.parseLong("0"))) {
                        controller.insertClinic(DentalArrList.get(i));
                    }

                }
            }

            }

    }

    private void errorOnExecuting(){
        this.cancel(true);
        /*new Handler(Looper.getMainLooper()).post(new Runnable() {
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