package com.fypj.insightsLocal.ui_logic;

/**
 * Created by L33525 on 22/9/2014.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.google_directions.Route;
import com.fypj.insightsLocal.google_directions.Routing;
import com.fypj.insightsLocal.google_directions.RoutingListener;
import com.fypj.insightsLocal.google_directions.Segment;
import com.fypj.insightsLocal.model.Event;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewClinicLocationFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Clinic clinic = new Clinic();
    private GoogleMap map;
    private LatLng destLatLng;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ViewClinicLocationFragment newInstance(int sectionNumber) {
        ViewClinicLocationFragment fragment = new ViewClinicLocationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewClinicLocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location last = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(last != null) {
            setHasOptionsMenu(true);
        }
        else{
            setHasOptionsMenu(false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.view_clinic_location,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if( id == R.id.directions){
            if(destLatLng != null) {
                Intent intent = new Intent(this.getActivity(), GetDirectionsActivity.class);
                intent.putExtra("lat", destLatLng.latitude);
                intent.putExtra("lng", destLatLng.longitude);
                intent.putExtra("address", clinic.getAddress());
                startActivity(intent);
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                builder.setTitle("Error in retrieving destination");
                builder.setMessage("Unable to retrieve destination. Please try again.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });
                builder.create().show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_clinic_location, container, false);
        // Get a handle to the Map Fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            clinic.setClinicID(bundle.getLong("clinicID"));
            clinic.setName(bundle.getString("name"));
            clinic.setAddress(bundle.getString("address"));
            clinic.setOperatingHours(bundle.getString("operatingHours"));
            clinic.setContactNo(bundle.getString("contactNo"));


        }

        // Get a handle to the Map Fragment
        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

        //LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Location last = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        List<Address> addressList = geocoding(clinic.getAddress());

        LatLng startLatLng = null;
        if (addressList != null) {
            for (int i = 0; i < addressList.size(); i++) {
                destLatLng = new LatLng(addressList.get(i).getLatitude(), addressList.get(i).getLongitude());
            }
            if (destLatLng != null) {
                Marker marker = map.addMarker(new MarkerOptions().title(clinic.getAddress()).position(destLatLng));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(destLatLng, 16));
                marker.showInfoWindow();
            } else {
                showSingapore(map);
            }
        } else {
            showSingapore(map);
        }

        ClinicSQLController controller = new ClinicSQLController(getActivity());
        ArrayList<Clinic> ClinicArrList = controller.getAllClinic();
        for (int i = 0; i < ClinicArrList.size(); i++) {
            if ((ClinicArrList.get(i).getName().equals(clinic.getName()))) {
                List<Address> checkAddressList = geocoding(clinic.getAddress());
                LatLng foundLatLng = null;
                if (checkAddressList != null) {
                    for (int j = 0; j < checkAddressList.size(); j++) {
                        destLatLng = new LatLng(addressList.get(j).getLatitude(), addressList.get(j).getLongitude());
                    }
                    if (destLatLng != null) {
                        Marker marker = map.addMarker(new MarkerOptions().title(clinic.getAddress()).position(destLatLng));
                    }
                }
            }
        }
        return rootView;
    }

    private List<Address> geocoding(String location){
            Geocoder geoCoder = new Geocoder(getActivity());
            List<Address> addressList = null;

            if(CheckNetworkConnection.isNetworkConnectionAvailable(ViewClinicLocationFragment.this.getActivity())) {
                try {
                    addressList = geoCoder.getFromLocationName(location, 1, 1.164632, 103.543739, 1.481777, 104.077263);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return addressList;
    }

    private void showSingapore(GoogleMap map){
        LatLng singapore = new LatLng(1.3450, 103.8250);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 10));
    }
}
