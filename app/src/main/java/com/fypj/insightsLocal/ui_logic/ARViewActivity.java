package com.fypj.insightsLocal.ui_logic;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ARViewActivity extends ActionBarActivity {
    private BeyondarFragmentSupport mBeyondarFragment;
    private Clinic clinic = new Clinic();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arview);
        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(R.id.beyondarFragment);

        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
            clinic.setAddress(savedInstanceState.getString("address"));
        }

        List<Address> addressList = geocoding(clinic.getAddress());

        LatLng latLng = null;
        if(addressList != null) {
            for (int i = 0; i < addressList.size(); i++) {
                latLng = new LatLng(addressList.get(i).getLatitude(), addressList.get(i).getLongitude());
            }
            if (latLng != null) {
            }
        }

        World world = new World(this);

// The user can set the default bitmap. This is useful if you are
// loading images form Internet and the connection get lost
        world.setDefaultImage(R.drawable.beyondar_default_unknow_icon);

// User position (you can change it using the GPS listeners form Android
// API)
        //world.setGeoPosition(103.8021433,1.4417591);
// User position (you can change it using the GPS listeners form Android
// API)
        world.setGeoPosition(41.26533734214473d, 1.925848038959814d);

// Create an object with an image in the app resources.
        GeoObject go1 = new GeoObject(1l);
        go1.setGeoPosition(41.26523339794433d, 1.926036406654116d);
        go1.setImageResource(R.drawable.medic);
        go1.setName("Creature 1");

// Create an object with an image in the app resources.
        GeoObject go2 = new GeoObject(12);

        go2.setGeoPosition(latLng.longitude,latLng.latitude);
        Toast.makeText(this,go2.getLatitude() + "," + go2.getLongitude(),Toast.LENGTH_LONG);
        go2.setImageResource(R.drawable.medic);
        go2.setName("Creature 2");

        world.addBeyondarObject(go1);
        world.addBeyondarObject(go2);

        mBeyondarFragment.setWorld(world);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.arview, menu);
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
