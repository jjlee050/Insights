package com.fypj.insightsLocal.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.ui_logic.ViewAllLatestEventsActivity;
import com.fypj.insightsLocal.util.HandleXML;

import java.util.Calendar;

/**
 * Created by L33525 on 23/10/2014.
 */
public class BackgroundReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    final static String TAG = "BootCompletedReceiver";
    final public static String ONE_TIME = "onetime";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "starting service...");
        Intent i = new Intent(context, BackgroundService.class);

        PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, 0);

        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        // 2000 = 2secs;
        // 60000 = 1mins;
        // 360000 = 1hrs;
        int min = 60000;
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), min * 1, pendingIntent);

    }
}