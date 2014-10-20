package com.fypj.insightsLocal.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fypj.insightsLocal.model.Clinic;
import com.fypj.insightsLocal.ui_logic.ViewClinicDetailsFragment;
import com.fypj.insightsLocal.ui_logic.ViewClinicLocationFragment;


/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ViewClinicDetailsAdapter extends FragmentPagerAdapter {
    private Context context;
    private Clinic clinic;
    public ViewClinicDetailsAdapter(Context context, FragmentManager fm, Clinic clinic) {
        super(fm);
        this.context = context;
        this.clinic = clinic;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch(position){
            case 0:
                fragment = new ViewClinicDetailsFragment();

                Bundle args = new Bundle();
                args.putLong("ClinicID", clinic.getClinicID());
                args.putString("ClinicName",clinic.getName());
                args.putString("ClinicAddress",clinic.getAddress());
                args.putString("ClinicOH",clinic.getOperatingHours());
                args.putString("ClinicContactNo",clinic.getContactNo());


                fragment.setArguments(args);
                break;
         case 1:
                fragment = new ViewClinicLocationFragment();

                Bundle args1 = new Bundle();
                args1.putLong("ClinicID", clinic.getClinicID());
                args1.putString("ClinicName",clinic.getName());
                args1.putString("ClinicAddress",clinic.getAddress());
                args1.putString("ClinicOH",clinic.getOperatingHours());
                args1.putString("ClinicContactNo",clinic.getContactNo());

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
