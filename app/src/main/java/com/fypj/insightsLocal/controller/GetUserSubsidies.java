package com.fypj.insightsLocal.controller;

import android.app.ProgressDialog;
import android.content.Context;
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
    private LoginActivity context;
    private ArrayList<UserSubsidies> userSubsidiesArrList = new ArrayList<UserSubsidies>();
    private ProgressDialog dialog;
    private String nric;
    private int status;


    public GetUserSubsidies(Context context, String nric, int status, ProgressDialog dialog){
        this.context = (LoginActivity) context;
        this.nric = nric;
        this.status = status;
        this.dialog = dialog;
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
