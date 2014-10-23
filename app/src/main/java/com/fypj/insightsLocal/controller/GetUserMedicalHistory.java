package com.fypj.insightsLocal.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.sqlite_controller.UserMedicalHistoriesSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.insightsLocal.ui_logic.LoginActivity;
import com.fypj.insightsLocal.util.ClinicHistoryListAdapter;
import com.fypj.mymodule.api.insightsMedicalHistory.InsightsMedicalHistory;
import com.fypj.mymodule.api.insightsMedicalHistory.model.MedicalHistory;
import com.fypj.mymodule.api.insightsUser.InsightsUser;
import com.fypj.mymodule.api.insightsUser.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 23/10/2014.
 */
public class GetUserMedicalHistory extends AsyncTask<Void,Void,List<MedicalHistory>> {
    private static InsightsMedicalHistory myApiService = null;
    private Activity context;
    private ArrayList<MedicalHistory> userMedicalHistoriesArrList = new ArrayList<MedicalHistory>();
    private ProgressDialog dialog;
    private String nric;
    private ListView lvClinicHistoryList;

    public GetUserMedicalHistory(Activity context, String nric, ListView lvClinicHistoryList){
        this.context = context;
        this.nric = nric;
        this.lvClinicHistoryList = lvClinicHistoryList;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Retriving user's medical information.", "Please wait...", true);
    }

    @Override
    protected List<MedicalHistory> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsMedicalHistoriesAPI();
        }
        try {
            return myApiService.listMedicalHistories().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting("Unable to retrieve user medical information. Please try again.");
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<MedicalHistory> result) {
        if(result != null) {
            for (MedicalHistory e : result) {
                userMedicalHistoriesArrList.add(e);
            }

            ArrayList<MedicalHistory> myMedicalHistoriesArrList = new ArrayList<MedicalHistory>();
            UserMedicalHistoriesSQLController controller = new UserMedicalHistoriesSQLController(context);
            if(userMedicalHistoriesArrList.size() > 0){
                controller.deleteAllMedicalHistories();
            }
            for(int i=0;i<userMedicalHistoriesArrList.size();i++){
                controller.insertUserMedicalHistory(userMedicalHistoriesArrList.get(i));
                if(userMedicalHistoriesArrList.get(i).getNric().equals(nric)){
                    myMedicalHistoriesArrList.add(userMedicalHistoriesArrList.get(i));
                }
            }

            ClinicHistoryListAdapter adapter = new ClinicHistoryListAdapter(context,android.R.id.text1,myMedicalHistoriesArrList);
            lvClinicHistoryList.setAdapter(adapter);
            dialog.dismiss();
        }
    }

    private void errorOnExecuting(final String message){
        this.cancel(true);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Error verifying user ");
                builder.setMessage(message);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }
}
