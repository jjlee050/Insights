package com.fypj.insightsLocal.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.ui_logic.ViewAllLatestEventsActivity;
import com.fypj.insightsLocal.util.HandleXML;

/**
 * Created by L33525 on 23/10/2014.
 */
public class BackgroundReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    final static String TAG = "BootCompletedReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "starting service...");
        Intent i = new Intent(context, BackgroundService.class);
        context.startService(i);
    }
}