package com.fypj.insightsLocal.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.insightsLocal.ui_logic.LoginActivity;
import com.fypj.mymodule.api.insightsUser.InsightsUser;
import com.fypj.mymodule.api.insightsUser.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jess on 20-Oct-14.
 */
public class GetUser extends AsyncTask<Void,Void,List<User>>{
    private static InsightsUser myApiService = null;
    private LoginActivity context;
    private ArrayList<User> userArrList = new ArrayList<User>();
    private ProgressDialog dialog;
    private String nric,password;

    public GetUser(Context context, String nric, String password){
        this.context = (LoginActivity) context;
        this.nric = nric;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Verifying user information.", "Please wait...", true);
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsUserAPI();
        }
        try {
            return myApiService.listUser().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting("Unable to verify user. Please try again.");
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<User> result) {
        if(result != null) {
            for (User e : result) {
                userArrList.add(e);
            }
            boolean recordExists = false;
            User foundUser = null;
            for(int i=0;i<userArrList.size();i++){
                if((userArrList.get(i).getNric().equals(nric)) && (userArrList.get(i).getPassword().equals(password))){
                    recordExists = true;
                    foundUser = userArrList.get(i);
                    break;
                }
            }
            if(recordExists){
                final UserSQLController controller = new UserSQLController(context);
                User user = controller.getUser(nric);
                int status = controller.getUserSignInStatus(foundUser.getNric());
                if(user.getNric().equals("")){
                    controller.insertUser(foundUser);
                    System.out.println("Status: " + status);
                }
                new GetUserPackages(context,nric,status).execute();
            }
            else{
                errorOnExecuting("There is no such user record.");
            }
            dialog.dismiss();
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
