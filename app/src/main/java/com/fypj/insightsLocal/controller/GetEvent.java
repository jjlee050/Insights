package com.fypj.insightsLocal.controller;

/**
 * Created by jess on 02-Nov-14.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.options.Settings;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.ui_logic.ViewEventActivity;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
/**
 * Created by L33525 on 13/10/2014.
 */
public class GetEvent extends AsyncTask<Void, Void, List<Event>> implements Settings {
    private static InsightsEvent myApiService = null;
    private Context context;
    final ArrayList<Event> latestEventArrList = new ArrayList<Event>();

    public GetEvent(Context context){
        this.context = (Context) context;
    }
    @Override
    protected List<Event> doInBackground(Void... voids) {
        if(myApiService == null) { // Only do this once
            myApiService = AppConstants.getInsightsEventAPI();
        }
        try {
            return myApiService.listEvents().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting();
            return Collections.EMPTY_LIST;
        }
    }
    @Override
    protected void onPostExecute(List<Event> result) {
        if(result != null) {
            for (Event e : result) {
                latestEventArrList.add(e);
            }

            EventSQLController controller = new EventSQLController(context);
            if(controller.getAllEvent().size() > 0) {
                controller.deleteAllEvents();
            }
            for(int i=0;i<latestEventArrList.size();i++) {
                Event event = controller.getEvent(latestEventArrList.get(i).getEventID());
                if(event.getEventID().equals(Long.parseLong("0"))) {
                    controller.insertEvent(latestEventArrList.get(i));
                }
            }
        }
    }
    private void errorOnExecuting(){
        this.cancel(true);
    }
}