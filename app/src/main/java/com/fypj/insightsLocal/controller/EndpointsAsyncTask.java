package com.fypj.insightsLocal.controller;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fypj.insightsLocal.options.Settings;
import com.fypj.mymodule.api.insightsClinics.InsightsClinics;
import com.fypj.mymodule.api.model.Clinic;
import com.fypj.mymodule.api.model.Event;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.appspot.insights_50.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by L33525 on 13/10/2014.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, List<Clinic>> implements Settings {
    private static InsightsClinics myApiService = null;
    private Context context;

    public EndpointsAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected List<Clinic> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            InsightsClinics.Builder builder = new InsightsClinics.Builder(AndroidHttp.newCompatibleTransport(),
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
            return myApiService.listClinics().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Event> result) {
        for (Event e : result) {
            Toast.makeText(context, e.getName() + " : " + e.getDesc(), Toast.LENGTH_LONG).show();
        }
    }
}
