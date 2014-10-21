package com.fypj.insightsLocal.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.options.AppConstants;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.insightsLocal.ui_logic.MainPageActivity;
import com.fypj.insightsLocal.ui_logic.ViewEventActivity;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.InsightsEvent;
import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.fypj.mymodule.api.insightsUser.InsightsUser;
import com.fypj.mymodule.api.insightsUser.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jess on 20-Oct-14.
 */
public class GetUser extends AsyncTask<Void,Void,List<User>>{
    private static InsightsUser myApiService = null;
    private Activity context;
    private ArrayList<User> userArrList = new ArrayList<User>();
    private ProgressDialog dialog;
    private String nric,password;

    public GetUser(Context context, String nric, String password){
        this.context = (Activity) context;
        this.nric = nric;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                "Verifying user information.", "Please wait...", true);
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            myApiService = AppConstants.getInsightsUserAPI();
        }
        try {
            return myApiService.listUser().execute().getItems();
        } catch (IOException e) {
            errorOnExecuting("Unable to verify user. Please try again.");
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<User> result) {
        if(result != null) {
            for (User e : result) {
                userArrList.add(e);
            }
            boolean recordExists = false;
            User foundUser = null;
            for(int i=0;i<userArrList.size();i++){
                if((userArrList.get(i).getNric().equals(nric)) && (userArrList.get(i).getPassword().equals(password))){
                    recordExists = true;
                    foundUser = userArrList.get(i);
                    break;
                }
            }
            if(recordExists){
                final UserSQLController controller = new UserSQLController(context);
                User user = controller.getUser(nric);
                if(user.getNric().equals("")){
                    controller.insertUser(foundUser);
                    int status = controller.getUserSignInStatus(foundUser.getNric());
                    System.out.println("Status: " + status);
                    if(status == 0) {
                        final AlertDialog alertDialog = createAlertDialogForPreferredLanguage(controller,foundUser);
                        // show it
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                    }
                    else if(status == 1){
                        goToMainPage();
                    }
                }
                else{
                    goToMainPage();
                }
            }
            else{
                errorOnExecuting("There is no such user record.");
            }
            dialog.dismiss();
        }
    }

    private void errorOnExecuting(final String message){
        this.cancel(true);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Error in verifying user ");
                builder.setMessage(message);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        cancel(true);
                    }
                });
                builder.create().show();
            }
        });
    }

    private AlertDialog createAlertDialogForPreferredLanguage(final UserSQLController controller, User foundUser){

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_set_preferred_language, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        // set dialog message
        alertDialogBuilder.setTitle("First-Time Login");
        // create alert dialog

        final Spinner mSpinner= (Spinner) promptsView.findViewById(R.id.sp_language);
        ArrayAdapter<String> spLanguageArrAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, context.getResources().getStringArray(R.array.language));
        mSpinner.setAdapter(spLanguageArrAdapter);

        final User finalFoundUser = foundUser;
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                controller.updateSignInStatus(finalFoundUser,1);
                controller.updatePreferredLanguage(finalFoundUser,mSpinner.getSelectedItem().toString());
                goToMainPage();
                cancel(true);
            }
        });
        return alertDialogBuilder.create();
    }

    public void goToMainPage(){
        writeData();
        Intent i = new Intent(context, MainPageActivity.class);
        context.startActivity(i);
        context.finish();
    }



    private void writeData(){
        SharedPreferences sharedPref = context.getSharedPreferences("insightsPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nric", nric);
        editor.putString("password", password);
        editor.commit();
    }

}
