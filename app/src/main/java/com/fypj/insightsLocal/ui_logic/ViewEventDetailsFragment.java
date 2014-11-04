package com.fypj.insightsLocal.ui_logic;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.Event;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.mymodule.api.insightsUser.model.User;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;

import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewEventDetailsFragment extends Fragment implements
        ConnectionCallbacks, OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    private TextToSpeech ttobj = null;
    private TextView tvEventName;
    private TextView tvEventDateAndTime;
    private TextView tvEventGuestOfHonour;
    private TextView tvEventDesc;
    private TextView tvEventOrganizer;
    private TextView tvEventContactNo;

    private String textSpeech;
    private String language;
    private Locale ttsLanguage;

    public ViewEventDetailsFragment newInstance(int sectionNumber) {
        ViewEventDetailsFragment fragment = new ViewEventDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        setHasOptionsMenu(true);
        return fragment;
    }

    public ViewEventDetailsFragment() {
        setHasOptionsMenu(true);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause(){
        if(ttobj !=null){
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onPause();
    }

    public void onStop() {
        super.onStop();
        if(mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                this.getActivity().startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
    }

    @Override
    public void onDisconnected() {

    }

    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.view_event,menu);
        MenuItem share = menu.findItem(R.id.share);
        if(!CheckNetworkConnection.isNetworkConnectionAvailable(ViewEventDetailsFragment.this.getActivity())) {
            share.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                if(CheckNetworkConnection.isNetworkConnectionAvailable(this.getActivity())) {
                    mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(Plus.API)
                            .addScope(Plus.SCOPE_PLUS_LOGIN)
                            .build();
                    mGoogleApiClient.connect();

                    Intent shareIntent = new PlusShare.Builder(this.getActivity()).setType("text/plain")
                            .setText(textSpeech)
                            .getIntent();

                    startActivityForResult(shareIntent, 0);
                }
                else{
                    CheckNetworkConnection.displayNoConnectionDialog(this.getActivity());
                }
                return true;
            case R.id.translate:
                boolean translatePackageExists = isPackageExisted("com.google.android.apps.translate");
                if(!translatePackageExists){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                    builder.setTitle("Unable to translate without Google Translate.");
                    builder.setMessage("Would you like to download Google Translate from Google Play Store?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                final String appPackageName = "com.google.android.apps.translate"; // getPackageName() from Context or Activity object
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                    // create alert dialog
                    AlertDialog alertDialog = builder.create();

                    // show it
                    alertDialog.show();
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.apps.translate");


                    Uri uri = new Uri.Builder()
                            .scheme("http")
                            .authority("translate.google.com")
                            .path("/m/translate")
                            .appendQueryParameter("q", textSpeech)
                            .appendQueryParameter("tl", language) // target language
                            .appendQueryParameter("sl", "en") // source language
                            .build();
                    //intent.setType("text/plain"); //not needed, but possible
                    intent.setData(uri);

                    startActivity(intent);
                }
                return true;
            case R.id.read_aloud:
                ttobj = new TextToSpeech(this.getActivity().getApplicationContext(),new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            //ttobj.setLanguage(Locale.)
                            ttobj.setLanguage(ttsLanguage);
                            ttobj.speak(textSpeech, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isPackageExisted(String targetPackage){
        PackageManager pm = this.getActivity().getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_event_details, container, false);
        Bundle bundle = getArguments();
        tvEventName = (TextView) rootView.findViewById(R.id.tv_event_name);
        tvEventDateAndTime = (TextView) rootView.findViewById(R.id.tv_event_date_time);
        tvEventGuestOfHonour = (TextView) rootView.findViewById(R.id.tv_event_goh);
        tvEventDesc = (TextView) rootView.findViewById(R.id.tv_event_desc);
        tvEventOrganizer = (TextView) rootView.findViewById(R.id.tv_event_organiser);
        tvEventContactNo = (TextView) rootView.findViewById(R.id.tv_event_contact);

        tvEventName.setText(bundle.getString("name"));
        tvEventDateAndTime.setText(bundle.getString("dateAndTime"));
        if(bundle.getString("guestOfHonour").equals("-")){
            tvEventGuestOfHonour.setVisibility(View.GONE);
            tvEventGuestOfHonour.setText("-");
        }
        else {
            tvEventGuestOfHonour.setVisibility(View.VISIBLE);
            tvEventGuestOfHonour.setText(bundle.getString("guestOfHonour"));
        }
        tvEventDesc.setText(bundle.getString("desc"));
        tvEventOrganizer.setText("Organized by: " + bundle.getString("organizer"));
        tvEventContactNo.setText("Contact " + bundle.getString("contactNo"));

        setSpeechAndLanguage(bundle);

        return rootView;
    }

    private void setSpeechAndLanguage(Bundle bundle){

        String textSpeech = tvEventName.getText() + " will be held on " + tvEventDateAndTime.getText()
                + " which is organized by " + bundle.getString("organizer") + ". This event is about " + tvEventDesc.getText();
        if((!bundle.getString("guestOfHonour").equals("-")) && (!bundle.getString("guestOfHonour").equalsIgnoreCase("Nil"))){
            textSpeech += ". The guest of honour for this event will be " + tvEventGuestOfHonour.getText();
        }
        textSpeech += ". " + tvEventContactNo.getText() + " for more information.";
        this.textSpeech = textSpeech;
        System.out.println(textSpeech);


        SharedPreferences sharedPref= this.getActivity().getSharedPreferences("insightsPreferences", Context.MODE_PRIVATE);
        String nric = sharedPref.getString("nric", "");
        String language = sharedPref.getString("preferred_language", "");
        if(!nric.equals("")){
            System.out.println("Language: " + language);
            if(language.equals("zh-CN")){
                ttsLanguage = Locale.CHINESE;
            }
            else{
                language = "en";
                ttsLanguage = Locale.ENGLISH;
            }
        }
        else{
            language = "en";
            ttsLanguage = Locale.ENGLISH;
        }
    }
}
