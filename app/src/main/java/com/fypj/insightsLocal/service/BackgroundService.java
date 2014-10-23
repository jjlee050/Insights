package com.fypj.insightsLocal.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.fypj.insightsLocal.controller.GetMedicalHistory;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.util.HandleXML;

public class BackgroundService extends Service {
    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("ConnectionChecker", "Connection Checker started");
        if(CheckNetworkConnection.isNetworkConnectionAvailable(this)) {
            HandleXML obj = new HandleXML("http://www.pa.gov.sg/index.php?option=com_events&view=events&rss=1&Itemid=170", this);
            obj.fetchXML();
            GetMedicalHistory getMedicalHistory = new GetMedicalHistory(this);
            getMedicalHistory.execute();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
