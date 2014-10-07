package com.fypj.insightsLocal.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fypj.insightsLocal.model.Event;
import com.fypj.insightsLocal.ui_logic.ViewEventDetailsFragment;
import com.fypj.insightsLocal.ui_logic.ViewEventLocationFragment;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewEventPagerAdapter extends FragmentPagerAdapter {
    private Event event;
    public ViewEventPagerAdapter(FragmentManager fm, Event event) {
        super(fm);
        this.event = event;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0:
                fragment = new ViewEventDetailsFragment();

                Bundle args = new Bundle();
                args.putString("name",event.getName());
                args.putString("dateAndTime",event.getDateAndTime());
                args.putString("guestOfHonour",event.getGuestOfHonour());
                args.putString("desc",event.getDesc());
                args.putString("organizer",event.getOrganizer());
                args.putString("contactNo",event.getContactNo());

                fragment.setArguments(args);
                break;
            case 1:
                fragment = new ViewEventLocationFragment();

                Bundle args1 = new Bundle();
                args1.putString("location",event.getLocation());

                fragment.setArguments(args1);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
