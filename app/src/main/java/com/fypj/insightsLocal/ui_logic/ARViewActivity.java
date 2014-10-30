package com.fypj.insightsLocal.ui_logic;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.view.BeyondarGLSurfaceView;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.view.OnTouchBeyondarViewListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.util.MyLocationListener;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ARViewActivity extends ActionBarActivity implements
        OnTouchBeyondarViewListener, OnClickBeyondarObjectListener {
    private BeyondarFragmentSupport mBeyondarFragment;
    private Clinic clinic = new Clinic();

    private static final String PROVIDER = "flp";
    private static final double LAT = 1.379032;
    private static final double LNG = 103.849789;
    private static final float ACCURACY = 5.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arview);
        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(R.id.beyondarFragment);

        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
            clinic.setAddress(savedInstanceState.getString("address"));
        }

        getSupportActionBar().hide();

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

        /* Use the LocationManager class to obtain GPS locations */

        /*LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);*/

        // User position (you can change it using the GPS listeners form Android
        // API)
        //world.setGeoPosition(103.8021433,1.4417591);
        // User position (you can change it using the GPS listeners form Android
        // API)
        //world.setGeoPosition(mlocListener.latitude, mlocListener.longitude);

        // Example of creating a new Location from test data
        Location testLocation = createLocation(LAT, LNG, ACCURACY);

        //Location testLocation = createLocation(latLng.latitude,latLng.longitude, ACCURACY);
        //world.setGeoPosition(LAT,LNG);
        world.setGeoPosition(1.3786117d, 103.8458455d);


        // Create an object with an image in the app resources.
        GeoObject go2 = new GeoObject(12);
        // Define a LocationClient object


        go2.setGeoPosition(1.3786117d, 103.8458455d);
        //go2.setGeoPosition(LAT, LNG);
        //go2.setGeoPosition(1.3799775, 103.848772);
        //go2.setGeoPosition(latLng.latitude,latLng.longitude);
        Log.i("Coordinate", go2.getLatitude() + "," + go2.getLongitude());
        Toast.makeText(this,go2.getLatitude() + "," + go2.getLongitude(),Toast.LENGTH_LONG);

        go2.setName(clinic.getAddress());

        world.addBeyondarObject(go2);

        mBeyondarFragment.setOnTouchBeyondarViewListener(this);
        mBeyondarFragment.setOnClickBeyondarObjectListener(this);

        mBeyondarFragment.setMaxDistanceToRender(50000);
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

    @Override
    public void onTouchBeyondarView(MotionEvent event, BeyondarGLSurfaceView beyondarView) {

        float x = event.getX();
        float y = event.getY();

        ArrayList<BeyondarObject> geoObjects = new ArrayList<BeyondarObject>();

        // This method call is better to don't do it in the UI thread!
        beyondarView.getBeyondarObjectsOnScreenCoordinates(x, y, geoObjects);

        String textEvent = "";

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                textEvent = "Event type ACTION_DOWN: ";
                break;
            case MotionEvent.ACTION_UP:
                textEvent = "Event type ACTION_UP: ";
                break;
            case MotionEvent.ACTION_MOVE:
                textEvent = "Event type ACTION_MOVE: ";
                break;
            default:
                break;
        }

        Iterator<BeyondarObject> iterator = geoObjects.iterator();
        while (iterator.hasNext()) {
            BeyondarObject geoObject = iterator.next();
            textEvent = textEvent + " " + geoObject.getName();

        }
    }

    @Override
    public void onClickBeyondarObject(ArrayList<BeyondarObject> beyondarObjects) {
        if (beyondarObjects.size() > 0) {
            Toast.makeText(this, "Clicked on: " + beyondarObjects.get(0).getName(),
                    Toast.LENGTH_LONG).show();
        }
    }


    /*
     * From input arguments, create a single Location with provider set to
     * "flp"
     */
    public Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }

}
