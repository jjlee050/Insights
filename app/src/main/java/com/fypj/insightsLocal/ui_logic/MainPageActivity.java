package com.fypj.insightsLocal.ui_logic;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.fypj.insightsLocal.util.NavigationDrawerFragment;
import com.fypj.insightsLocal.R;


public class MainPageActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private String mTitle;
    private int currentPosition = 0;

    public String getMTitle() {
        return mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, homeFragment.newInstance(this,position + 1))
                        .commit();
                break;
            case 1:
                ViewAllPioneerPackagesFragment viewAllPioneerPackagesFragment = new ViewAllPioneerPackagesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, viewAllPioneerPackagesFragment.newInstance(this,position + 1))
                        .commit();
                break;
            case 3:
                ViewAllLatestEventsFragment viewAllLatestEventsFragment = new ViewAllLatestEventsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, viewAllLatestEventsFragment.newInstance(this,position + 1))
                        .commit();
                break;
        }
        currentPosition = position;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Home";
                break;
            case 2:
                mTitle = "CHAS Clinic Locator";
                break;
            case 3:
                mTitle = "Check Eligibility";
                break;
            case 4:
                mTitle = "Latest Events";
                break;
            case 5:
                mTitle = "Settings";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            switch(currentPosition){
                case 0:
                    getMenuInflater().inflate(R.menu.main_page, menu);
                    restoreActionBar();
                    break;
                case 3:
                    getMenuInflater().inflate(R.menu.latest_events, menu);
                    restoreActionBar();
                    break;
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

}
