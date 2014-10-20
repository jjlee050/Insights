package com.fypj.insightsLocal.ui_logic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.fypj.insightsLocal.util.NavigationDrawerFragment;
import com.fypj.insightsLocal.R;

/**
 * This is the activity class to show the screen with navigational drawer
 */
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
    public static int currentPosition;

    /**
     * This is the getter method for mTitle
     * @return String
     */
    public String getMTitle() {
        return mTitle;
    }

    /**
     * This is the setter method for mTitle
     * @param mTitle
     */
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
        fragmentManager.popBackStack();
        Intent i = null;
        switch(position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, homeFragment.newInstance(this,position + 1))
                        .commit();
                break;
            case 1:
                i = new Intent(this,ProfileActivity.class);
                startActivity(i);
                break;
            case 2:
                i = new Intent(this,ViewAllPioneerPackagesActivity.class);
                startActivity(i);
                break;
            case 3:
                i = new Intent(this,ViewAllLatestEventsActivity.class);
                i.putExtra("choice", 0);
                startActivity(i);
                break;
            case 4:
                i = new Intent(this,NearestClinicActivity.class);
                i.putExtra("choice",0);
                startActivity(i);
                break;
            case 6:
                i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
        currentPosition = position;
    }

    /**
     * This method is to do the intent part for the respective activity class
     * @param number
     */
    public void onSectionAttached(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 1:
                mTitle = "Home";
                break;
            case 2:
                mTitle = "My Profile";
                break;
            case 3:
                mTitle = "Pioneer Generation Packages";
                break;
            case 4:
                mTitle = "Lifestyle Events";
                break;
            case 5:
                mTitle = "CHAS Clinic";
                break;
            case 7:
                mTitle = "Log out";
                break;
        }
        System.out.println("Title: " + mTitle);
    }

    /**
     * This method is to reinitialize the action bar
     */
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            switch(currentPosition){
                case 0:
                    getMenuInflater().inflate(R.menu.main_page, menu);
                    restoreActionBar();
                    break;
                case 1:
                    getMenuInflater().inflate(R.menu.profile, menu);
                    restoreActionBar();
                    break;
                case 2:
                    getMenuInflater().inflate(R.menu.pioneer_packages, menu);
                    restoreActionBar();
                    break;
                case 3:
                    getMenuInflater().inflate(R.menu.latest_events, menu);
                    restoreActionBar();
                    break;
                case 4:
                    getMenuInflater().inflate(R.menu.nearest_clinc, menu);
                    restoreActionBar();
                    break;
                case 6:
                    getMenuInflater().inflate(R.menu.login, menu);
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
        if(id == R.id.check_eligibility){
            Intent intent = new Intent(this,CheckEligibilityActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
