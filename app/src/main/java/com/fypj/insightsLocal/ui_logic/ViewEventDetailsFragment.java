package com.fypj.insightsLocal.ui_logic;


import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;

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
        mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:


                mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(Plus.API)
                        .addScope(Plus.SCOPE_PLUS_LOGIN)
                        .build();

                Intent shareIntent = new PlusShare.Builder(this.getActivity()).setType("text/plain")
                        .setText("Welcome to the Google+ platform.")
                        .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                        .getIntent();

                startActivityForResult(shareIntent, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        System.out.println("Request Code: " + requestCode);
        if(requestCode == 0){
            Toast.makeText(this.getActivity(),"Share successful",Toast.LENGTH_LONG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_event_details, container, false);
        Bundle bundle = getArguments();
        TextView tvEventName = (TextView) rootView.findViewById(R.id.tv_event_name);
        TextView tvEventDateAndTime = (TextView) rootView.findViewById(R.id.tv_event_date_time);
        TextView tvEventGuestOfHonour = (TextView) rootView.findViewById(R.id.tv_event_goh);
        TextView tvEventDesc = (TextView) rootView.findViewById(R.id.tv_event_desc);
        TextView tvEventOrganizer = (TextView) rootView.findViewById(R.id.tv_event_organiser);
        TextView tvEventContactNo = (TextView) rootView.findViewById(R.id.tv_event_contact);

        tvEventName.setText(bundle.getString("name"));
        tvEventDateAndTime.setText(bundle.getString("dateAndTime"));
        tvEventGuestOfHonour.setText(bundle.getString("guestOfHonour"));
        tvEventDesc.setText(bundle.getString("desc"));
        tvEventOrganizer.setText(bundle.getString("organizer"));
        tvEventContactNo.setText(bundle.getString("contactNo"));
        return rootView;
    }


}
