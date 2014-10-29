package com.fypj.insightsLocal.util;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by jess on 29-Oct-14.
 */
/* Class My Location Listener */

public class MyLocationListener implements LocationListener {
    public static double latitude = 0;
    public static double longitude = 0;
    @Override
    public void onLocationChanged(Location loc){
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        /*String Text = “My current location is: “ +
        “Latitud = “ + loc.getLatitude() + “Longitud = “ + loc.getLongitude();*/
        //Toast.makeText( getApplicationContext(), Text, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onProviderDisabled(String provider){
        //Toast.makeText( getApplicationContext(),“Gps Disabled”, Toast.LENGTH_SHORT ).show();
    }


    @Override
    public void onProviderEnabled(String provider){
        //Toast.makeText( getApplicationContext(),“Gps Enabled”,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){


    }

}/* End of Class MyLocationListener */
