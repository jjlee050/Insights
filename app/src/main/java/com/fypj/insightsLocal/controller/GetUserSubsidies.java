package com.fypj.insightsLocal.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.sqlite_controller.UserPackagesSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSubsidiesSQLController;
import com.fypj.insightsLocal.ui_logic.LoginActivity;
import com.fypj.mymodule.api.insightsUserPackages.InsightsUserPackages;
import com.fypj.mymodule.api.insightsUserPackages.model.UserPackages;
import com.fypj.mymodule.api.insightsUserSubsidies.InsightsUserSubsidies;
import com.fypj.mymodule.api.insightsUserSubsidies.model.UserSubsidies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by L33525 on 27/10/2014.
 */
public class GetUserSubsidies extends AsyncTask<Void, Void, List<UserSubsidies>> {
    private static InsightsUserSubsidies myApiService = null;
    private Context context;
    private ArrayList<UserSubsidies> userSubsidiesArrList = new ArrayList<UserSubsidies>();
    private String nric;


    public GetUserSubsidies(Context context, String nric){
        this.context = context;
        this.nric = nric;
    }


    @Override
    protected List<UserSubsidies> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsUserSubsidiesAPI();
        }
        try {
            return myApiService.listUserSubsidies().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting("Unable to retrieve user. Please try again.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<UserSubsidies> result) {
        if(result != null) {
            for (UserSubsidies e : result) {
                userSubsidiesArrList.add(e);
            }
            boolean recordExists = false;
            ArrayList<UserSubsidies> foundUserSubsidies = new ArrayList<UserSubsidies>();

            for(int i=0;i<userSubsidiesArrList.size();i++){
                if((userSubsidiesArrList.get(i).getNric().equals(nric))){
                    recordExists = true;
                    foundUserSubsidies.add(userSubsidiesArrList.get(i));
                }
            }
            if(recordExists){
                final UserSubsidiesSQLController controller = new UserSubsidiesSQLController(context);
                controller.deleteAllUserSubsidies();
                for(int i=0;i<foundUserSubsidies.size();i++) {
                    UserSubsidies userSubsidies = controller.getUserSubsidies(nric, foundUserSubsidies.get(i).getSubsidiesID());
                    if(userSubsidies.getSubsidiesID() == 0){
                        controller.insertUserSubsidies(foundUserSubsidies.get(i));
                    }
                }
            }
        }

    }

    private void errorOnExecuting(final String message){
        this.cancel(true);
    }

}
