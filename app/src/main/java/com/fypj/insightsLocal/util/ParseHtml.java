package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.fypj.insightsLocal.controller.CreateEvent;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.ui_logic.ViewAllLatestEventsActivity;
import com.fypj.mymodule.api.insightsEvent.model.Event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by L33525 on 14/10/2014.
 */
public class ParseHtml extends AsyncTask<Void, Void, String> {
    private ViewAllLatestEventsActivity activity;
    private String title,link,description;
    private Event event = new Event();
    private ArrayList<String> valueArrList = new ArrayList<String>();

    public ParseHtml(ViewAllLatestEventsActivity activity, String title, String link, String description){
        this.activity = (ViewAllLatestEventsActivity)activity;
        this.title = title;
        this.link = link;
        this.description = description;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuffer buffer = new StringBuffer();
        try {
            Log.d("JSwa", "Connecting to [" + link + "]");
            Document doc = Jsoup.connect(link).get();
            Log.d("JSwa", "Connected to [" + link + "]");

            Elements eventName = doc.select(".block h1");
            buffer.append("Event name \r\n");
            for (Element event : eventName) {
                String data = event.text().trim();

                buffer.append("[" + data + "] \r\n");
                title = data;
            }

            Elements topicList = doc.select(".block table tr td:nth-child(2)");
            buffer.append("Topic list\r\n");
            for (Element topic : topicList) {
                String data = topic.text().trim();

                buffer.append("[" + data + "] \r\n");
                System.out.println(data);
                valueArrList.add(data);
            }

        } catch (Throwable t) {
            this.cancel(true);
            t.printStackTrace();
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(valueArrList.size() > 0) {
            event.setName(title);
            event.setDateAndTime(valueArrList.get(0));
            event.setLocation(valueArrList.get(1).substring(1));
            event.setGuestOfHonour(valueArrList.get(2).substring(1));
            event.setOrganizer(valueArrList.get(3).substring(1));
            String contactNo = valueArrList.get(4).substring(valueArrList.get(4).length() - 8, valueArrList.get(4).length());
            String contactPerson = valueArrList.get(4).substring(1, valueArrList.get(4).length()-9);
            event.setContactNo(contactPerson + " at " + contactNo);
            if(description != "") {
                event.setDesc(description);
            }
            else{
                event.setDesc(valueArrList.get(5).substring(1));
            }
            System.out.println(valueArrList.get(0) + "\r\n Date: " + valueArrList.get(0).substring(0,valueArrList.get(0).lastIndexOf("to")));
            DateFormat df = new SimpleDateFormat("dd MMMM yyyy h:mma");
            try {
                Date dt = df.parse(valueArrList.get(0).substring(0,valueArrList.get(0).lastIndexOf("to")-1));
                Date dnow = new Date();
                Calendar ca = Calendar.getInstance();
                Calendar cnow = Calendar.getInstance();
                ca.setTime(dt);
                cnow.setTime(dnow);
                if(cnow.getTimeInMillis() - ca.getTimeInMillis() < 0){
                    EventSQLController controller = new EventSQLController(activity);
                    ArrayList<Event> eventArrList = controller.getAllEvent();
                    if(eventArrList.size() > 0) {
                        boolean duplicate = false;
                        for (int i = 0; i < eventArrList.size(); i++) {
                            if ((eventArrList.get(i).getName().equals(event.getName())) || (eventArrList.get(i).getDesc().equals(event.getDesc()))) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            createEvent(event);
                        }
                    }
                    else{
                        createEvent(event);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void createEvent(Event event){
        if((event.getName().contains("Walk") || (event.getDesc().contains("Walk")))) {
            CreateEvent createEvent = new CreateEvent(activity, event);
            createEvent.execute();
        }
        else{
            return;
        }
    }

}