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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fypj.insightsLocal.options.Settings;
import com.fypj.insightsLocal.ui_logic.ViewEventActivity;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 13/10/2014.
 */
public class GetEvents extends AsyncTask<Void, Void, List<Event>> implements Settings {
    private static InsightsEvent myApiService = null;
    private Activity context;
    private ListView lvEvents;
    private ProgressDialog dialog;

    public GetEvents(Context context, ListView lvEvents){
        this.context = (Activity) context;
        this.lvEvents = lvEvents;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Retrieving latest event", "Please wait...", true);
    }

    @Override
    protected List<Event> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            InsightsEvent.Builder builder = new InsightsEvent.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(LOCAL_API_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
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
        final ArrayList<Event> latestEventArrList = new ArrayList<Event>();

        for (Event e : result) {
            latestEventArrList.add(e);
            //Toast.makeText(context, e.getName() + " : " + e.getDesc(), Toast.LENGTH_LONG).show();
        }

        LatestEventsListAdapter adapter = new LatestEventsListAdapter(context, android.R.id.text1, latestEventArrList);
        lvEvents.setAdapter(adapter);

        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(context, ViewEventActivity.class);
                intent.putExtra("id", latestEventArrList.get(position).getEventID());
                intent.putExtra("name", latestEventArrList.get(position).getName());
                intent.putExtra("dateAndTime", latestEventArrList.get(position).getDateAndTime());
                intent.putExtra("guestOfHonour", latestEventArrList.get(position).getGuestOfHonour());
                intent.putExtra("desc", latestEventArrList.get(position).getDesc());
                intent.putExtra("organizer", latestEventArrList.get(position).getOrganizer());
                intent.putExtra("contactNo", latestEventArrList.get(position).getContactNo());
                intent.putExtra("location", latestEventArrList.get(position).getLocation());
                context.startActivity(intent);
            }
        });

        dialog.dismiss();
    }

    private void errorOnExecuting(){
        this.cancel(true);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Error in retrieving event ");
                builder.setMessage("Unable to retrieve event. Please try again.");
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
