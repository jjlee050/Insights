package com.fypj.insightsLocal.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.ui_logic.ViewAllLatestEventsActivity;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsEvent.model.Event;

/**
 * Created by L33525 on 14/10/2014.
 */
public class CreateEvent extends AsyncTask<Void, Void, Boolean> {
    private static InsightsEvent myApiService = null;
    private ViewAllLatestEventsActivity activity;
    private Event event;
    private ProgressDialog dialog;

    public CreateEvent(ViewAllLatestEventsActivity activity, Event event){
        this.activity = (ViewAllLatestEventsActivity) activity;
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
            return false;
        }
    }

    private void errorOnExecuting(){
        this.cancel(true);
    }
}
