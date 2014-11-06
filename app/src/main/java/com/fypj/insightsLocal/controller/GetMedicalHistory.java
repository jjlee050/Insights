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
import com.fypj.mymodule.api.myMedicalHistory.MyMedicalHistory;
import com.fypj.mymodule.api.myMedicalHistory.model.MedicalHistory;
import com.fypj.mymodule.api.insightsUser.InsightsUser;
import com.fypj.mymodule.api.insightsUser.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 23/10/2014.
 */
public class GetMedicalHistory extends AsyncTask<Void,Void,List<MedicalHistory>> {
    private static MyMedicalHistory myApiService = null;
    private Context context;
    private ArrayList<MedicalHistory> userMedicalHistoriesArrList = new ArrayList<MedicalHistory>();

    public GetMedicalHistory(Context context){
        this.context = context;
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
                for(int i=0;i<userMedicalHistoriesArrList.size();i++){
                    if(controller.getMedicalHistory(userMedicalHistoriesArrList.get(i).getMedicalHistoryID()).getMedicalHistoryID().equals(Long.parseLong("0")))
                    {
                        controller.insertUserMedicalHistory(userMedicalHistoriesArrList.get(i));
                    }
                }
            }
            else{

            }

        }
    }

    private void errorOnExecuting(final String message){
        this.cancel(true);
    }
}
