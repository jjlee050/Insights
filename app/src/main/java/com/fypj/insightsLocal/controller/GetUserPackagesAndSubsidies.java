package com.fypj.insightsLocal.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

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
public class GetUserPackagesAndSubsidies extends AsyncTask<Void, Void, Void> {
    private static InsightsUserPackages myApiService = null;
    private static InsightsUserSubsidies myApiService1 = null;
    private LoginActivity context;
    private List<UserPackages> userPackagesArrList = new ArrayList<UserPackages>();
    private List<UserSubsidies> userSubsidiesArrList = new ArrayList<UserSubsidies>();
    private ProgressDialog dialog;
    private String nric;
    private int status;


    public GetUserPackagesAndSubsidies(Context context, String nric, int status){
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
    protected Void doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightUserPackagesAPI();
        }
        if(myApiService1 == null){
            myApiService1 = AppConstants.getInsightsUserSubsidiesAPI();
        }
        try {
            userPackagesArrList = myApiService.listUserPackages().execute().getItems();
            userSubsidiesArrList = myApiService1.listUserSubsidies().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting("Unable to retrieve user. Please try again.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(userPackagesArrList != null) {
            for (UserPackages e : userPackagesArrList) {
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

        if(userSubsidiesArrList != null) {
            for (UserSubsidies e : userSubsidiesArrList) {
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
                for(int i=0;i<foundUserSubsidies.size();i++) {
                    UserSubsidies userSubsidies = controller.getUserSubsidies(nric, foundUserSubsidies.get(i).getSubsidiesID());
                    if(userSubsidies.getSubsidiesID() == 0){
                        controller.insertUserSubsidies(foundUserSubsidies.get(i));
                    }
                }
            }
        }
        dialog.dismiss();


        if((status == 0) || (status == -1)) {
            context.goToMainPage();
            context.goToSettingsPage(nric);
        }
        else if(status == 1){
            context.goToMainPage();
        }
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
