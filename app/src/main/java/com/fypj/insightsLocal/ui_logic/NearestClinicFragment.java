package com.fypj.insightsLocal.ui_logic;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetClinic;
import com.fypj.insightsLocal.controller.GetEvents;
import com.fypj.insightsLocal.util.ClinicAdapter;
import  com.fypj.insightsLocal.model.Clinic;

public class NearestClinicFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    MainPageActivity activity;

    private SwipeRefreshLayout swipeView;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
   // SectionsPagerAdapter mSectionsPagerAdapter;

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
                intent.putExtra("ClinicName",ClinicArrList.get(position).getName());
                intent.putExtra("ClinicOH ",ClinicArrList.get(position).getOperatingHours());

                startActivity(intent);
            }
        });*/


        swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);

        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeView.setEnabled(false);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAllClinic(lvNearestClinic);
                    }
                }, 1000);
            }
        });

        lvNearestClinic.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeView.setEnabled(true);
                else
                    swipeView.setEnabled(false);
            }
        });
        getAllClinic(lvNearestClinic);


        return rootView;
    }


        public SwipeRefreshLayout getSwipeView() {
            return swipeView;
        }

        public void setSwipeView(SwipeRefreshLayout swipeView) {
            this.swipeView = swipeView;
        }



        private void getAllClinic(ListView lvNearestClinic){
            new GetClinic(NearestClinicFragment.this.getActivity(),lvNearestClinic,swipeView).execute();

        }


    }




