package com.fypj.insightsLocal.ui_logic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetClinic;
import com.fypj.insightsLocal.controller.GetDental;

import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.insightsLocal.util.DentalAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class NearestDentalFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    MainPageActivity activity;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private SwipeRefreshLayout swipeView;

    public NearestDentalFragment newInstance(MainPageActivity activity,int sectionNumber) {
        NearestDentalFragment fragment = new NearestDentalFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        this.activity = activity;
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }
    public NearestDentalFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_nearest_dental, container, false);

        final ListView lvNearestDental = (ListView) rootView.findViewById(R.id.lv_nearest_dental);

        /*
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
                        getAllClinic(lvNearestDental); }
                }, 1000);
            }
        });

        lvNearestDental.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        getAllClinic(lvNearestDental);


        return rootView;
    }


    public SwipeRefreshLayout getSwipeView() {
        return swipeView;
    }

    public void setSwipeView(SwipeRefreshLayout swipeView) {
        this.swipeView = swipeView;
    }



    private void getAllClinic(ListView lvNearestDental){

        ClinicSQLController clinicSQLController = new ClinicSQLController(this.getActivity());

        final ArrayList<com.fypj.mymodule.api.insightsClinics.model.Clinic> ClinicArrList = clinicSQLController.getAllCategoryClinic("Dental");

        DentalAdapter adapter = new DentalAdapter(this.getActivity(), android.R.id.text1, ClinicArrList);
        lvNearestDental.setAdapter(adapter);

        lvNearestDental.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(NearestDentalFragment.this.getActivity(), ViewClinicActivity.class);
                intent.putExtra("clinicID", ClinicArrList.get(position).getClinicID());
                intent.putExtra("name", ClinicArrList.get(position).getName());
                intent.putExtra("address", ClinicArrList.get(position).getAddress());
                intent.putExtra("operatingHours", ClinicArrList.get(position).getOperatingHours());
                intent.putExtra("contactNo", ClinicArrList.get(position).getContactNo());
                intent.putExtra("category", ClinicArrList.get(position).getCategory());;

                startActivity(intent);
            }
        });
        }

    }





