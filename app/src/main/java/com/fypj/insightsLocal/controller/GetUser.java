package com.fypj.insightsLocal.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.insightsLocal.ui_logic.LoginActivity;
import com.fypj.insightsLocal.ui_logic.MainPageActivity;
import com.fypj.insightsLocal.ui_logic.ViewEventActivity;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsEvent.model.Event;
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
                if(user.getNric().equals("")){
                    controller.insertUser(foundUser);
                    int status = controller.getUserSignInStatus(foundUser.getNric());
                    System.out.println("Status: " + status);
                    if(status == 0) {
                        context.goToMainPage();
                        context.goToSettingsPage();
                    }
                    else if(status == 1){
                        context.goToMainPage();
                    }
                }
                else{
                    context.goToMainPage();
                }
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
