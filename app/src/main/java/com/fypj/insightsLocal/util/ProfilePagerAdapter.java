package com.fypj.insightsLocal.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.fypj.insightsLocal.ui_logic.ViewClinicHistoryFragment;
import com.fypj.insightsLocal.ui_logic.ViewProfileFragment;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {
    private JazzyViewPager mJazzy;

    public ProfilePagerAdapter(FragmentManager fm, JazzyViewPager mJazzy) {
        super(fm);
        this.mJazzy = mJazzy;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new ViewProfileFragment();
                break;
            case 1:
                fragment = new ViewClinicHistoryFragment();
                break;
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
        // Show 3 total pages.
        return 2;
    }
}
