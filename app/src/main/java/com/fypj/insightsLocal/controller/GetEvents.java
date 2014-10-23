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
import com.fypj.insightsLocal.options.Settings;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.ui_logic.ViewEventActivity;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsEvent.model.Event;

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
public class GetEvents extends AsyncTask<Void, Void, List<Event>> implements Settings {
    private static InsightsEvent myApiService = null;
    private Activity context;
    private ListView lvEvents;
    private SwipeRefreshLayout swipeView;
    final ArrayList<Event> latestEventArrList = new ArrayList<Event>();
    private ProgressDialog dialog;

    public GetEvents(Context context, ListView lvEvents, SwipeRefreshLayout swipeView){
        this.context = (Activity) context;
        this.lvEvents = lvEvents;
        this.swipeView = swipeView;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Retrieving latest event", "Please wait...", true);
    }

    @Override
    protected List<Event> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsEventAPI();
        }
        try {
            return myApiService.listEvents().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting();
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Event> result) {
        if(result != null) {
            for (Event e : result) {
                latestEventArrList.add(e);
            }
            sortDate();

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

            EventSQLController controller = new EventSQLController(context);
            if (controller.getAllEvent().size() > 0) {
                controller.deleteAllEvents();
            }
            for (int i = 0; i < latestEventArrList.size(); i++) {
                controller.insertEvent(latestEventArrList.get(i));
            }
            dialog.dismiss();
            swipeView.setRefreshing(false);
        }
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
        swipeView.setRefreshing(false);
    }

    private void sortDate(){
        Collections.sort(latestEventArrList, new Comparator<Event>() {
            public int compare(Event o1, Event o2) {

                DateFormat df = new SimpleDateFormat("dd MMMM yyyy h:mma");
                try {
                    Date dt = df.parse(o1.getDateAndTime().substring(0,o1.getDateAndTime().lastIndexOf("to")));
                    Date dt2 = df.parse(o2.getDateAndTime().substring(0,o2.getDateAndTime().lastIndexOf("to")));
                    if (dt == null || dt2 == null)
                        return 0;
                    return dt.compareTo(dt2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}