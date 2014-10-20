package com.fypj.insightsLocal.ui_logic;

/**
 * Created by L33525 on 22/9/2014.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
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
import android.widget.Toast;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.Event;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
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

    private Event event;

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
        Bundle bundle = getArguments();
        if(bundle != null) {
            String address = bundle.getString("location");
            event = new Event(bundle.getLong("id"),bundle.getString("name"),bundle.getString("dateAndTime"),bundle.getString("guestOfHonour"),bundle.getString("desc"),bundle.getString("organizer"),bundle.getString("contactNo"),address);
        }
        Toast.makeText(this.getActivity(),event.getLocation(),Toast.LENGTH_LONG).show();

        // Get a handle to the Map Fragment
        GoogleMap map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);

        Geocoder geoCoder = new Geocoder(getActivity());
        List<Address> addressList = null;
        try {
            addressList = geoCoder.getFromLocationName(event.getLocation(),1,1.164632,103.543739,1.481777,104.077263);
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
                Marker marker = map.addMarker(new MarkerOptions().title(event.getLocation()).position(latLng));
                marker.showInfoWindow();
            }
            else{
                showSingapore(map);
            }
        }
        else{
            showSingapore(map);
        }


        return rootView;
    }

    private void showSingapore(GoogleMap map){
        LatLng singapore = new LatLng(1.3450, 103.8250);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 10));
    }

}
