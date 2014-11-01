package com.fypj.insightsLocal.ui_logic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.google_directions.Route;
import com.fypj.insightsLocal.google_directions.Routing;
import com.fypj.insightsLocal.google_directions.RoutingListener;
import com.fypj.insightsLocal.google_directions.Segment;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.util.DirectionsListAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetDirectionsActivity extends ActionBarActivity implements RoutingListener {
    private TextView tvAddress;
    private ListView lvDirections;

    private LatLng startLatLng;
    private LatLng destLatLng;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);

        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
            double lat = savedInstanceState.getDouble("lat");
            double lng = savedInstanceState.getDouble("lng");
            address = savedInstanceState.getString("address");
            destLatLng = new LatLng(lat,lng);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvAddress = (TextView) findViewById(R.id.tv_address_to_address);
        lvDirections = (ListView) findViewById(R.id.lv_directions);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location last = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(last != null) {
            startLatLng = new LatLng(last.getLatitude(),last.getLongitude());
            //startLatLng = new LatLng(1.441338,103.802331);
            System.out.println("LatLng: " + last.getLatitude() + "," + last.getLongitude());
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error in retrieving directions ");
            builder.setMessage("Unable to retrieve directions. Please turn on location Services.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
            builder.create().show();
            //Toast.makeText(this.getActivity(), "", Toast.LENGTH_LONG).show();
        }


        List<Address> addressList = reverseGeocoding();
        if(addressList != null){
            String addressText = "";
            for(int i=0;i<addressList.get(0).getMaxAddressLineIndex();i++) {
                addressText += addressList.get(0).getAddressLine(i);
            }
            tvAddress.setText(addressText + "\n to \n"+ address);
        }

        Routing routing = new Routing(Routing.TravelMode.TRANSIT);
        routing.registerListener(this);
        routing.execute(startLatLng, destLatLng);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.get_directions, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Address> reverseGeocoding(){
        Geocoder geoCoder = new Geocoder(this);
        List<Address> addressList = null;

        if(CheckNetworkConnection.isNetworkConnectionAvailable(this)) {
            try {
                addressList = geoCoder.getFromLocation(startLatLng.latitude,startLatLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addressList;
    }

    @Override
    public void onRoutingFailure() {
    }
    @Override
    public void onRoutingStart() {
    }
    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
        ArrayList<Segment> segmentsArrList = (ArrayList<Segment>) route.getSegments();

        DirectionsListAdapter adapter = new DirectionsListAdapter(this, android.R.id.text1, segmentsArrList);
        lvDirections.setAdapter(adapter);
    }
}
