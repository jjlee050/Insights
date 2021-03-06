package com.fypj.insightsLocal.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.ui_logic.ViewAllLatestEventsActivity;
import com.fypj.mymodule.api.insightsEvent.model.Event;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by L33525 on 14/10/2014.
 */
public class HandleXML {

    private String urlString = null;
    private Context context;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;
    private ArrayList<ParseHtml> mTasks = new ArrayList<ParseHtml>();

    private ProgressDialog dialog;

    public HandleXML(String url, Context context){
        this.urlString = url;
        this.context = context;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:

                        String title = "";
                        String link = "";
                        String description = "";
                        if(name.equals("title")){
                            title = text;
                        }
                        else if(name.equals("link")){
                            link = text;
                        }
                        else if(name.equals("description")){
                            description = text;
                        }
                        if((link != "")) {
                            System.out.println("Link: " + link);
                            ParseHtml parseHtml = new ParseHtml(context, title, link, description);
                            if(!link.equals("http://www.pa.gov.sg/events.html?view=events")) {
                                mTasks.add(parseHtml);
                            }
                        }
                        break;
                }
                event = myParser.next();
            }
            for(int i=0;i<mTasks.size();i++){
                mTasks.get(i).execute();
            }

            int numberOfTasks = mTasks.size();
            int finishedTasks = mTasks.size();
            while(finishedTasks > 0){
                for(int i=0;i<mTasks.size();i++){
                    if(mTasks.get(i).getStatus() == AsyncTask.Status.FINISHED){
                        finishedTasks -= 1;
                    }
                }
                if(finishedTasks > 0){
                    finishedTasks = numberOfTasks;
                }
                else{
                    handler.sendMessage(handler.obtainMessage());
                }
            }

            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();
                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);
                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //activity.getAllEvents();
        }
    };
}