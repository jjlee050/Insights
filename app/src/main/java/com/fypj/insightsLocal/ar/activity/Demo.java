package com.fypj.insightsLocal.ar.activity;

import java.io.IOException;
import java.util.ArrayList;


import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.ar.activity.AugmentedReality;

import com.fypj.insightsLocal.ar.data.ARData;
import com.fypj.insightsLocal.ar.data.GooglePlacesDataSource;
import com.fypj.insightsLocal.ar.data.LocalDataSource;
import com.fypj.insightsLocal.ar.data.NetworkDataSource;
import com.fypj.insightsLocal.ar.data.WikipediaDataSource;
import com.fypj.insightsLocal.ar.ui.IconMarker;
import com.fypj.insightsLocal.ar.ui.Marker;
import com.fypj.insightsLocal.ar.widgets.VerticalTextView;
import com.fypj.insightsLocal.ar.widgets.VerticalTextView;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.insightsLocal.ui_logic.ViewClinicActivity;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Demo extends AugmentedReality {
	
    private static final String TAG = "Demo";
    private static final String locale = Locale.getDefault().getLanguage();
    private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
    private static final ThreadPoolExecutor exeService = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, queue);
    private static final Map<String, NetworkDataSource> sources = new ConcurrentHashMap<String, NetworkDataSource>();

    private static Toast myToast = null;
    private static VerticalTextView text = null;

    private ArrayList<String> clinicIDStringList = new ArrayList<String>();
    private ArrayList<String> nameStringList = new ArrayList<String>();
    private ArrayList<String> categoryStringList = new ArrayList<String>();
    private ArrayList<String> addressStringList = new ArrayList<String>();
    private ArrayList<String> operatingHoursStringList = new ArrayList<String>();
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
            clinicIDStringList = savedInstanceState.getStringArrayList("clinicIDList");

            nameStringList = savedInstanceState.getStringArrayList("nameList");
            categoryStringList = savedInstanceState.getStringArrayList("categoryList");

            addressStringList = savedInstanceState.getStringArrayList("addressList");
            operatingHoursStringList = savedInstanceState.getStringArrayList("operatingHoursList");
        }
     
        // Create toast
        myToast = new Toast(getApplicationContext());
        myToast.setGravity(Gravity.CENTER, 0, 0);
        // Creating our custom text view, and setting text/rotation
        text = new VerticalTextView(getApplicationContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = 350;
        text.setLayoutParams(params);
        text.setBackgroundResource(android.R.drawable.toast_frame);
        text.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Small);
        text.setShadowLayer(2.75f, 0f, 0f, Color.parseColor("#BB000000"));
        myToast.setView(text);
        // Setting duration and displaying the toast
        myToast.setDuration(Toast.LENGTH_SHORT);
        
       
        // Local
        
        ArrayList<Marker> markerList = new ArrayList<Marker>();
        LocalDataSource localData = new LocalDataSource(this.getResources());

        for(int i=0;i<addressStringList.size();i++) {
            List<Address> addressList = geocoding(addressStringList.get(i));

            LatLng latLng = null;
            if(addressList != null) {
                for (int j = 0; j < addressList.size(); j++) {
                    latLng = new LatLng(addressList.get(j).getLatitude(), addressList.get(j).getLongitude());
                }

                Marker marker = null;
                Resources res = getResources();
                if(categoryStringList.get(i).equals("Medical")) {
                    Bitmap medic = BitmapFactory.decodeResource(res, R.drawable.medic);
                    marker = new IconMarker(nameStringList.get(i) + "\n" + operatingHoursStringList.get(i), latLng.latitude, latLng.longitude, localData.getMarkers().get(0).getHeight(), localData.getMarkers().get(0).getColor(),medic);
                }
                else{
                    Bitmap dental = BitmapFactory.decodeResource(res, R.drawable.dental);
                    marker = new IconMarker(nameStringList.get(i) + "\n" + operatingHoursStringList.get(i), latLng.latitude, latLng.longitude, localData.getMarkers().get(0).getHeight(), localData.getMarkers().get(0).getColor(),dental);
                }

                markerList.add(marker);


            }
        }

        ARData.addMarkers(markerList);
        ARData.addMarkers(localData.getMarkers());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        super.onStart();

        Location last = ARData.getCurrentLocation();
        updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG, "onOptionsItemSelected() item=" + item);
        switch (item.getItemId()) {
            case R.id.showRadar:
                showRadar = !showRadar;
                item.setTitle(((showRadar) ? "Hide" : "Show") + " Radar");
                break;
            case R.id.showZoomBar:
                showZoomBar = !showZoomBar;
                item.setTitle(((showZoomBar) ? "Hide" : "Show") + " Zoom Bar");
                zoomLayout.setVisibility((showZoomBar) ? LinearLayout.VISIBLE : LinearLayout.GONE);
                break;
            case R.id.exit:
                finish();
                break;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);

        updateData(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void markerTouched(Marker marker) {
        /*text.setText(marker.getName());
        myToast.show();*/

        ClinicSQLController controller = new ClinicSQLController(this);
        ArrayList<Clinic> clinicArrList = controller.getAllClinic();
        Clinic clinic = null;
        for(int i=0;i<clinicArrList.size();i++){
            String markerName = clinicArrList.get(i).getName() + "\n" + clinicArrList.get(i).getOperatingHours();
            System.out.println(markerName.equals(marker.getName()));
            if(markerName.equals(marker.getName())){
                clinic = clinicArrList.get(i);
                break;
            }
        }

        Intent intent = new Intent(this, ViewClinicActivity.class);
        intent.putExtra("clinicID", clinic.getClinicID());
        intent.putExtra("name", clinic.getName());
        intent.putExtra("address", clinic.getAddress());
        intent.putExtra("operatingHours", clinic.getOperatingHours());
        intent.putExtra("contactNo", clinic.getContactNo());
        intent.putExtra("category", clinic.getCategory());

        this.startActivity(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateDataOnZoom() {
        super.updateDataOnZoom();
        Location last = ARData.getCurrentLocation();
        updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
    }

    private void updateData(final double lat, final double lon, final double alt) {
        try {
            exeService.execute(new Runnable() {
                @Override
                public void run() {
                    for (NetworkDataSource source : sources.values())
                        download(source, lat, lon, alt);
                }
            });
        } catch (RejectedExecutionException rej) {
            Log.w(TAG, "Not running new download Runnable, queue is full.");
        } catch (Exception e) {
            Log.e(TAG, "Exception running download Runnable.", e);
        }
    }

    private static boolean download(NetworkDataSource source, double lat, double lon, double alt) {
        if (source == null) return false;

        String url = null;
        try {
            url = source.createRequestURL(lat, lon, alt, ARData.getRadius(), locale);
        } catch (NullPointerException e) {
            return false;
        }

        List<Marker> markers = null;
        try {
            markers = source.parse(url);
        } catch (NullPointerException e) {
            return false;
        }

        ARData.addMarkers(markers);
        return true;
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
