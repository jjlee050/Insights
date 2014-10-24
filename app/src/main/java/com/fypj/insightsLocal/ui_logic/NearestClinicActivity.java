package com.fypj.insightsLocal.ui_logic;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
//import com.fypj.insightsLocal.model.Clinic;
import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.Dental;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.insightsLocal.util.ClinicAdapter;
import com.fypj.insightsLocal.util.HandleXML;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;

import java.util.ArrayList;

public class NearestClinicActivity extends ActionBarActivity implements ActionBar.TabListener {



    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    private ListView lvNearestClinic;
    private ArrayList<Clinic> ClinicArrList;
    private ArrayList<Clinic> searchResultsArrList;
    private SwipeRefreshLayout swipeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_clinc);

        lvNearestClinic = (ListView) findViewById(R.id.lv_nearest_clinic);
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeView.setEnabled(false);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh(true);
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

    }

    private void refresh(boolean refresh) {

        ClinicSQLController controller = new ClinicSQLController(NearestClinicActivity.this);

        ClinicArrList = controller.getAllClinic();


        ClinicAdapter adapter = new ClinicAdapter(NearestClinicActivity.this, android.R.id.text1, ClinicArrList);
        lvNearestClinic.setAdapter(adapter);

        lvNearestClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(NearestClinicActivity.this, NearestClinicActivity.class);
                intent.putExtra("clinicID", searchResultsArrList.get(position).getClinicID());
                intent.putExtra("name", searchResultsArrList.get(position).getName());
                intent.putExtra("address", searchResultsArrList.get(position).getAddress());
                intent.putExtra("operatingHours", searchResultsArrList.get(position).getOperatingHours());
                intent.putExtra("contactNo", searchResultsArrList.get(position).getContactNo());
                startActivity(intent);
            }
        });
        if(refresh) {
            swipeView.setRefreshing(false);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nearest_clinc, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if(searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //System.out.println("Query: " + query);
                    ClinicSQLController controller = new ClinicSQLController(NearestClinicActivity.this);
                    searchResultsArrList = controller.searchClinic(query);

                    ClinicAdapter adapter = new ClinicAdapter(NearestClinicActivity.this, android.R.id.text1, searchResultsArrList);
                    lvNearestClinic.setAdapter(adapter);

                 lvNearestClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                         Intent intent = new Intent(NearestClinicActivity.this, NearestClinicActivity.class);
                         intent.putExtra("clinicID", searchResultsArrList.get(position).getClinicID());
                         intent.putExtra("name", searchResultsArrList.get(position).getName());
                         intent.putExtra("address", searchResultsArrList.get(position).getAddress());
                         intent.putExtra("operatingHours", searchResultsArrList.get(position).getOperatingHours());
                         intent.putExtra("contactNo", searchResultsArrList.get(position).getContactNo());

                         startActivity(intent);
                     }
                 });
                    return false;
                }
            });
        }
        return true;
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
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
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new NearestClinicFragment();
                    break;
                case 1:
                    fragment = new NearestDentalFragment();
                    break;
            }
            return fragment;
            //return NearestClinicFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

    }

}



