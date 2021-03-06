package com.fypj.insightsLocal.ui_logic;

import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.ar.ui.Marker;
import com.fypj.insightsLocal.google_directions.Route;
import com.fypj.insightsLocal.google_directions.Routing;
import com.fypj.insightsLocal.google_directions.RoutingListener;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.util.ViewClinicDetailsAdapter;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;


public class ViewClinicActivity extends ActionBarActivity implements ActionBar.TabListener ,LocationListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    ViewClinicDetailsAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    JazzyViewPager mJazzy;
    private Clinic clinic = new Clinic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__clinic);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);


        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){

            Long id = savedInstanceState.getLong("clinicID");
            String name = savedInstanceState.getString("name");
            String address = savedInstanceState.getString("address");
            String operatingHours = savedInstanceState.getString("operatingHours");
            String contactNo = savedInstanceState.getString("contactNo");
            String category = savedInstanceState.getString("category");

            actionBar.setTitle(name);
            clinic.setClinicID(id);
            clinic.setName(name);
            clinic.setAddress(address);
            clinic.setOperatingHours(operatingHours);
            clinic.setContactNo(contactNo);
            clinic.setCategory(category);

        }

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        // Getting Current Location From GPS
        Location location = locationManager.getLastKnownLocation(provider);
        if(location!=null){
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20000, 0, this);

        // Getting URL to the Google Directions API
            /*Intent intent = new Intent(this,Demo2.class);
            startActivity(intent);*/

        Location last = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        // Set up the ViewPager with the sections adapter.
        mJazzy = (JazzyViewPager) findViewById(R.id.pager);
        mJazzy.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        mJazzy.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ViewClinicDetailsAdapter(ViewClinicActivity.this,getSupportFragmentManager(),clinic,mJazzy);

        mJazzy.setAdapter(mSectionsPagerAdapter);

        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_info_outline_white_24dp).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_place_white_24dp).setTabListener(this));

    }




    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mJazzy.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private JazzyViewPager mJazzy;
        public SectionsPagerAdapter(FragmentManager fm, Clinic clinic, JazzyViewPager mJazzy) {
            super(fm);
            this.mJazzy = mJazzy;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
         Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ViewClinicDetailsFragment();
                    break;
               case 1:
                    fragment = new ViewClinicLocationFragment();
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }


    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private List<Address> geocoding(String location){
        Geocoder geoCoder = new Geocoder(this);
        List<Address> addressList = null;

        if(CheckNetworkConnection.isNetworkConnectionAvailable(this)) {
            try {
                addressList = geoCoder.getFromLocationName(location, 1, 1.164632, 103.543739, 1.481777, 104.077263);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addressList;
    }


}
