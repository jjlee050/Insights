package com.fypj.insightsLocal.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.sqlite_controller.UserPackagesSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSubsidiesSQLController;
import com.fypj.insightsLocal.ui_logic.LoginActivity;
import com.fypj.mymodule.api.insightsUser.model.User;
import com.fypj.mymodule.api.insightsUserPackages.InsightsUserPackages;
import com.fypj.mymodule.api.insightsUserPackages.model.UserPackages;
import com.fypj.mymodule.api.insightsUserSubsidies.InsightsUserSubsidies;
import com.fypj.mymodule.api.insightsUserSubsidies.model.UserSubsidies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by L33525 on 27/10/2014.
 */
public class GetUserPackages extends AsyncTask<Void, Void, List<UserPackages>> {
    private static InsightsUserPackages myApiService = null;
    private LoginActivity context;
    private List<UserPackages> userPackagesArrList = new ArrayList<UserPackages>();
    private ProgressDialog dialog;
    private String nric;
    private int status;


    public GetUserPackages(Context context, String nric, int status){
        this.context = (LoginActivity) context;
        this.nric = nric;
        this.status = status;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Retrieving user information.", "Please wait...", true);
    }

    @Override
    protected List<UserPackages> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightUserPackagesAPI();
        }
        try {
            return myApiService.listUserPackages().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting("Unable to retrieve user. Please try again.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<UserPackages> result) {
        if(result != null) {
            for (UserPackages e : result) {
                userPackagesArrList.add(e);
            }
            boolean recordExists = false;
            ArrayList<UserPackages> foundUserPackages = new ArrayList<UserPackages>();

            for(int i=0;i<userPackagesArrList.size();i++){
                if((userPackagesArrList.get(i).getNric().equals(nric))){
                    recordExists = true;
                    foundUserPackages.add(userPackagesArrList.get(i));
                }
            }
            Log.i("Size", String.valueOf(foundUserPackages.size()));
            if(recordExists){
                final UserPackagesSQLController controller = new UserPackagesSQLController(context);
                for(int i=0;i<foundUserPackages.size();i++) {
                    UserPackages userPackages = controller.getUserPackages(nric, foundUserPackages.get(i).getPackagesID());
                    if(userPackages.getPackagesID() == 0){
                        controller.insertUserPackage(foundUserPackages.get(i));
                    }
                }
            }
        }

        new GetUserSubsidies(context,nric,status,dialog).execute();
    }

    private void errorOnExecuting(final String message){
        this.cancel(true);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                dialog.dismiss();
                context.errorOnExecuting(message);
            }
        });
    }

}
