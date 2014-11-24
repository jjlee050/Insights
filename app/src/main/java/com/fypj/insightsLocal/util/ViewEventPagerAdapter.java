package com.fypj.insightsLocal.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.ui_logic.ViewEventDetailsFragment;
import com.fypj.insightsLocal.ui_logic.ViewEventLocationFragment;
import com.fypj.mymodule.api.insightsEvent.model.Event;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewEventPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private Event event;
    private JazzyViewPager mJazzy;

    public ViewEventPagerAdapter(Context context,FragmentManager fm, Event event, JazzyViewPager mJazzy) {
        super(fm);
        this.context = context;
        this.event = event;
        this.mJazzy = mJazzy;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(CheckNetworkConnection.isNetworkConnectionAvailable(context)) {
            switch (position) {
                case 0:
                    fragment = new ViewEventDetailsFragment();

                    Bundle args = new Bundle();
                    args.putLong("id", event.getEventID());
                    args.putString("name", event.getName());
                    args.putString("dateAndTime", event.getDateAndTime());
                    args.putString("guestOfHonour", event.getGuestOfHonour());
                    args.putString("desc", event.getDesc());
                    args.putString("organizer", event.getOrganizer());
                    args.putString("contactNo", event.getContactNo());

                    fragment.setArguments(args);
                    break;
                case 1:
                    fragment = new ViewEventLocationFragment();

                    Bundle args1 = new Bundle();
                    args1.putLong("id", event.getEventID());
                    args1.putString("name", event.getName());
                    args1.putString("dateAndTime", event.getDateAndTime());
                    args1.putString("guestOfHonour", event.getGuestOfHonour());
                    args1.putString("desc", event.getDesc());
                    args1.putString("organizer", event.getOrganizer());
                    args1.putString("contactNo", event.getContactNo());
                    args1.putString("location", event.getLocation());

                    fragment.setArguments(args1);
                    break;
            }
        }
        else{
            switch (position) {
                case 0:
                    fragment = new ViewEventDetailsFragment();

                    Bundle args = new Bundle();
                    args.putLong("id", event.getEventID());
                    args.putString("name", event.getName());
                    args.putString("dateAndTime", event.getDateAndTime());
                    args.putString("guestOfHonour", event.getGuestOfHonour());
                    args.putString("desc", event.getDesc());
                    args.putString("organizer", event.getOrganizer());
                    args.putString("contactNo", event.getContactNo());

                    fragment.setArguments(args);
                    break;
            }
        }
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        mJazzy.setObjectForPosition(obj, position);
        return obj;
    }

    @Override
    public int getCount() {
        if(CheckNetworkConnection.isNetworkConnectionAvailable(context)) {
            // Show 2 total pages.
            return 2;
        }
        else{
            // Show 1 total pages.
            return 1;
        }
    }
}
