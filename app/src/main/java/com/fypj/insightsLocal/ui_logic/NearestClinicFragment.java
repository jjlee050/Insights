package com.fypj.insightsLocal.ui_logic;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.util.ClinicAdapter;
import  com.fypj.insightsLocal.model.Clinic;

public class NearestClinicFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    MainPageActivity activity;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public NearestClinicFragment newInstance(MainPageActivity activity,int sectionNumber) {
        NearestClinicFragment fragment = new NearestClinicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        this.activity = activity;
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }
    public NearestClinicFragment() {
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.nearest_clinc, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nearest_clinc, container, false);

        getActivity().getActionBar().setTitle("CHAS Clinics");
        final ListView lvNearestClinic = (ListView) rootView.findViewById(R.id.lv_nearest_clinic);

        /*final ArrayList<Clinic> ClinicArrList = new ArrayList<Clinic>();
        ClinicArrList.add(new Clinic(1,"338 Family Clinic","Mon - Thurs: \n8.30am - 12.30pm,\n2.00pm- 4.30pm,\n7.00pm - 9.00pm\nFri-Sun: 8.30am - 12.30pm\n(Closed on Public Holidays)"));
        ClinicArrList.add(new Clinic(2, "Accord Medical Clinic", "Mon - Fri: 8.30am - 10.00pm \n\nSat, Sun & PH :\n9.00am - 12.30pm,\n7.00pm - 10.00pm"));
        ClinicArrList.add(new Clinic(3, "Ang Mo Kio Family Medicine Clinic Pte Ltd", "Mon - Fri:\n8.30am - 3pm,\n6.00pm - 10.00pm\n\nSat: 9.00am - 10.00pm\nSun: 2.00pm - 9.00pm\n(Closed on Public Holidays)"));


        ClinicAdapter adapter = new ClinicAdapter(this.getActivity(), android.R.id.text1, ClinicArrList);
        lvNearestClinic.setAdapter(adapter);

        lvNearestClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(NearestClinicFragment.this.getActivity(),ViewClinicActivity.class);
                intent.putExtra("ClinicID", position);
                intent.putExtra("ClinicName",ClinicArrList.get(position).getClinicName());
                intent.putExtra("ClinicOH ",ClinicArrList.get(position).getClinicOH());

                startActivity(intent);
            }
        });*/
        return rootView;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }




    }

}
