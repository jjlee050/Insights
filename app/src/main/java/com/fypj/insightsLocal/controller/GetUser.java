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
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
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
    private Activity context;
    private ArrayList<User> userArrList = new ArrayList<User>();
    private ProgressDialog dialog;
    private String nric,password;

    public GetUser(Context context, String nric, String password){
        this.context = (Activity) context;
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
            errorOnExecuting();
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
            for(int i=0;i<userArrList.size();i++){
                if((userArrList.get(i).getNric().equals(nric)) && (userArrList.get(i).getPassword().equals(password))){
                    recordExists = true;
                    break;
                }
            }
            if(recordExists){
                
            }
            dialog.dismiss();
        }
    }

    private void errorOnExecuting(){
        this.cancel(true);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Error in verifying user ");
                builder.setMessage("Unable to verify user. Please try again.");
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
    }
}
