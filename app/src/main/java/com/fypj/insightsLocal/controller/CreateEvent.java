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
import com.fypj.insightsLocal.ui_logic.ViewEventActivity;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsEvent.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 14/10/2014.
 */
public class CreateEvent extends AsyncTask<Void, Void, Boolean> {
    private static InsightsEvent myApiService = null;
    private Activity context;
    private Event event;
    private ProgressDialog dialog;

    public CreateEvent(Context context, Event event){
        this.context = (Activity) context;
        this.event = event;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsEventAPI();
        }
        try {
            String text = "";
            Event createdEvent = myApiService.insertEvent(event).execute();
            if(createdEvent != null) {
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            errorOnExecuting();
            e.printStackTrace();
            return false;
        }
    }

    private void errorOnExecuting(){
        this.cancel(true);
    }
}
