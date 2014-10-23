package com.fypj.insightsLocal.options;

import com.fypj.mymodule.api.insightsClinics.InsightsClinics;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsMedicalHistory.InsightsMedicalHistory;
import com.fypj.mymodule.api.insightsUser.InsightsUser;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import java.io.IOException;

/**
 * Created by L33525 on 14/10/2014.
 */
public class AppConstants implements Settings{

    /**
     * Class instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    /**
     * Class instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static InsightsClinics insightsClinicsAPI;

    private static final String url = REMOTE_API_URL;
    /**
     * Retrieve insights event api service handle to access the API.
     */
    public static InsightsEvent getInsightsEventAPI() {
        // Use a builder to help formulate the API request.
        InsightsEvent.Builder builder = new InsightsEvent.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), null)
            .setRootUrl(url).setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
            @Override
            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
            }
        });
        return builder.build();
    }

    public static InsightsClinics getInsightsClinicsAPI() {

        InsightsClinics.Builder builder = new InsightsClinics.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), null)
                .setRootUrl(url).setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        return builder.build();

    }

    public static InsightsUser getInsightsUserAPI() {

        InsightsUser.Builder builder = new InsightsUser.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), null)
                .setRootUrl(url).setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        return builder.build();

    }

    public static InsightsMedicalHistory getInsightsMedicalHistoriesAPI() {

        InsightsMedicalHistory.Builder builder = new InsightsMedicalHistory.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), null)
                .setRootUrl(url).setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        return builder.build();

    }
}