package com.fypj.insightsLocal.controller;

import android.os.AsyncTask;

import org.apache.http.client.HttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jess on 09-Oct-14.
 */
public class TranslateWord extends AsyncTask<Object, Object, Object> {
    @Override
    protected String doInBackground(Object... Objects) {
        String responseBody = "";
        // Instantiate an HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://www.googleapis.com/language/translate/v2/languages?key=AIzaSyBBhQjmi8onbM5X5akIeDv-QfDopWjQzzg&source=en&target=de&q=Hello%20world&q=My%20name%20is%20Jeff");
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(httppost, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(responseBody);
        return responseBody;
    }

    /*@Override
    protected void onPostExecute(String result){
        JSONObject json;
        try {
            json = new JSONObject(result);
            boolean success = json.getBoolean("success");
            System.out.println(result);
        } catch (Exception e) {
            errorOnExecuting();
            e.printStackTrace();
        }
    }*/
}
