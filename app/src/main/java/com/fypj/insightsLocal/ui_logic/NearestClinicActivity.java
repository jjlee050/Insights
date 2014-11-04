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
import com.fypj.insightsLocal.ar.activity.Demo;
import com.fypj.insightsLocal.model.Dental;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.insightsLocal.util.ClinicAdapter;
import com.fypj.insightsLocal.util.HandleXML;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;

import java.util.ArrayList;

public class NearestClinicActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ListView lvNearestClinic;
    private ArrayList<Clinic> clinicArrList;
    private ArrayList<Clinic> searchResultsArrList;
    private SwipeRefreshLayout swipeView;

    SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_clinc);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);
        savedInstanceState = getIntent().getExtras();
// Create the adapter that will return a fragment for each of the three
// primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
// Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        actionBar.setTitle("CHAS Clinics");
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    actionBar.setTitle("CHAS Clinics");
                }
                else{
                    actionBar.setTitle("CHAS Dental");
                }
                actionBar.setSelectedNavigationItem(position);
            }
        });
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.white_medic).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.white_tooth).setTabListener(this));
        if(savedInstanceState != null){
            int position = savedInstanceState.getInt("choice");
            actionBar.setSelectedNavigationItem(position);
        }
    }
   /* private void refresh(boolean refresh) {

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
*/

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

                    int choice = getSupportActionBar().getSelectedNavigationIndex();
                    if(choice == 0) {

                        searchResultsArrList = controller.searchClinic(query,"Medical");
                        lvNearestClinic = (ListView) findViewById(R.id.lv_nearest_clinic);
                    }
                    else{

                        searchResultsArrList = controller.searchClinic(query,"Dental");
                        lvNearestClinic = (ListView) findViewById(R.id.lv_nearest_dental);
                    }

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
        else if( id == R.id.arView){
            /*Intent intent = new Intent(ViewClinicActivity.this, ARViewActivity.class);
            intent.putExtra("address", clinic.getAddress());
            startActivity(intent);*/
            ClinicSQLController controller = new ClinicSQLController(NearestClinicActivity.this);
            ArrayList<Clinic> clinicArrList = controller.getAllClinic();
            ArrayList<String> clinicIDList = new ArrayList<String>();
            ArrayList<String> nameList = new ArrayList<String>();
            ArrayList<String> categoryList = new ArrayList<String>();
            ArrayList<String> addressList = new ArrayList<String>();
            ArrayList<String> operatingHoursList = new ArrayList<String>();
            for(int i=0;i<clinicArrList.size();i++){
                clinicIDList.add(String.valueOf(clinicArrList.get(i).getClinicID()));
                nameList.add(clinicArrList.get(i).getName());
                categoryList.add(clinicArrList.get(i).getCategory());
                addressList.add(clinicArrList.get(i).getAddress());
                operatingHoursList.add(clinicArrList.get(i).getOperatingHours());
            }
            Intent intent = new Intent(this, Demo.class);
            intent.putStringArrayListExtra("clinicIDList",clinicIDList);
            intent.putStringArrayListExtra("nameList",nameList);
            intent.putStringArrayListExtra("categoryList",categoryList);
            intent.putStringArrayListExtra("addressList",addressList);
            intent.putStringArrayListExtra("operatingHoursList",operatingHoursList);
            startActivity(intent);
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



