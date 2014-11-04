package com.fypj.insightsLocal.ui_logic;

/**
 * Created by L33525 on 22/9/2014.
 */

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fypj.insightsLocal.R;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewClinicLocationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Clinic clinic = new Clinic();
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
        Toast.makeText(this.getActivity(), clinic.getAddress(), Toast.LENGTH_LONG).show();

        // Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);

        /*Geocoder geoCoder = new Geocoder(getActivity());
        List<Address> addressList = null;
        try {
            addressList = geoCoder.getFromLocationName(clinic.getAddress(),1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        LatLng latLng = null;
        if(addressList != null) {
            for (int i = 0; i < addressList.size(); i++) {
                latLng = new LatLng(addressList.get(i).getLatitude(), addressList.get(i).getLongitude());
            }
            if(latLng != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                Marker marker = map.addMarker(new MarkerOptions().title("Ang Mo Kio Community Centre").position(latLng));
                marker.showInfoWindow();
            }
            else{
                showSingapore(map);
            }
        }
        else{
            showSingapore(map);
        }*/

        List<Address> addressList = geocoding(clinic.getAddress());

        LatLng latLng = null;
        if (addressList != null) {
            for (int i = 0; i < addressList.size(); i++) {
                latLng = new LatLng(addressList.get(i).getLatitude(), addressList.get(i).getLongitude());
            }
            if (latLng != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                Marker marker = map.addMarker(new MarkerOptions().title(clinic.getAddress()).position(latLng));
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
                        latLng = new LatLng(addressList.get(j).getLatitude(), addressList.get(j).getLongitude());
                    }
                    if (latLng != null) {
                        Marker marker = map.addMarker(new MarkerOptions().title(clinic.getAddress()).position(latLng));
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
