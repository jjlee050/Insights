package com.fypj.insightsLocal.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.fypj.insightsLocal.ui_logic.ViewClinicDetailsFragment;
import com.fypj.insightsLocal.ui_logic.ViewClinicLocationFragment;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;


/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewClinicDetailsAdapter extends FragmentPagerAdapter {
    private Context context;
    private Clinic clinic;
    private JazzyViewPager mJazzy;

    public ViewClinicDetailsAdapter(Context context, FragmentManager fm, Clinic clinic, JazzyViewPager mJazzy) {
        super(fm);
        this.context = context;
        this.clinic = clinic;
        this.mJazzy = mJazzy;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
                fragment = new ViewClinicDetailsFragment();

                Bundle args = new Bundle();
                args.putLong("clinicID", clinic.getClinicID());
                args.putString("name", clinic.getName());
                args.putString("address", clinic.getAddress());
                args.putString("operatingHours", clinic.getOperatingHours());
                args.putString("contactNo", clinic.getContactNo());
                args.putString("category", clinic.getCategory());


                fragment.setArguments(args);
                break;
            case 1:
                fragment = new ViewClinicLocationFragment();

                Bundle args1 = new Bundle();
                args1.putLong("clinicID", clinic.getClinicID());
                args1.putString("name", clinic.getName());
                args1.putString("address", clinic.getAddress());
                args1.putString("operatingHours", clinic.getOperatingHours());
                args1.putString("contactNo", clinic.getContactNo());
                args1.putString("category", clinic.getCategory());

                fragment.setArguments(args1);
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
        // Show 2 total pages.
        return 2;
    }
}
