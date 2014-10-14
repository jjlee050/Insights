package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fypj.mymodule.api.insightsEvent.model.Event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by L33525 on 14/10/2014.
 */
public class ParseHtml extends AsyncTask<Void, Void, String> {
    private Activity context;
    private String link;
    private Event event = new Event();
    private ArrayList<String> valueArrList = new ArrayList<String>();

    public ParseHtml(Context context, String title, String link, String description){
        this.context = (Activity)context;
        this.link = link;
        event.setName(title);
        event.setDesc(description);
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuffer buffer = new StringBuffer();
        try {
            Log.d("JSwa", "Connecting to [" + link + "]");
            Document doc = Jsoup.connect(link).get();
            Log.d("JSwa", "Connected to [" + link + "]");
            Elements topicList = doc.select(".block table tr td:nth-child(2)");
            buffer.append("Topic list\r\n");
            for (Element topic : topicList) {
                String data = topic.text();

                buffer.append("[" + data + "] \r\n");
                valueArrList.add(data);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        if(valueArrList.size() > 0) {
            event.setDateAndTime(valueArrList.get(0));
            event.setLocation(valueArrList.get(1));
            event.setGuestOfHonour(valueArrList.get(2));
            event.setOrganizer(valueArrList.get(3));
            String contactNo = valueArrList.get(4).substring(valueArrList.get(4).length() - 8, valueArrList.get(4).length());
            String contactPerson = valueArrList.get(4).substring(0, valueArrList.get(4).length()-9);
            event.setContactNo(contactPerson + " at " + contactNo);
        }
    }
}