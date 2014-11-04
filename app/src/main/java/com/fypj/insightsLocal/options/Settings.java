package com.fypj.insightsLocal.options;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by L33525 on 3/10/2014.
 */
public interface Settings {
    String LOCAL_API_URL = "http://10.0.3.2:8080/_ah/api/";
    String REMOTE_API_URL = "https://insights-50.appspot.com/_ah/api/";

    public static final String BROADCAST = "com.fypj.insightsLocal.android.action.broadcast";
}
