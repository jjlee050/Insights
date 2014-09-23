package com.fypj.insightsLocal.ui_logic;

/**
 * Created by L33525 on 22/9/2014.
 */

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fypj.insightsLocal.R;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fypj.insightsLocal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewEventLocationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ViewEventLocationFragment newInstance(int sectionNumber) {
        ViewEventLocationFragment fragment = new ViewEventLocationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewEventLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_event_location, container, false);
        // Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

        Geocoder geoCoder = new Geocoder(getActivity());
        List<Address> addressList = null;
        try {
            addressList = geoCoder.getFromLocationName("795 Ang Mo Kio Avenue 1, 569976",1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LatLng latLng = null;
        for(int i=0;i<addressList.size();i++){
            latLng = new LatLng(addressList.get(i).getLatitude(), addressList.get(i).getLongitude());
        }

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        Marker marker = map.addMarker(new MarkerOptions().title("Ang Mo Kio Community Centre").position(latLng));
        marker.showInfoWindow();
        return rootView;
    }
}
