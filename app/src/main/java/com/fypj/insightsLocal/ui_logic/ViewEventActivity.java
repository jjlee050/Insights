package com.fypj.insightsLocal.ui_logic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.Event;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.util.ViewEventPagerAdapter;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;


public class ViewEventActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    ViewEventPagerAdapter mSectionsPagerAdapter;
    JazzyViewPager mJazzy;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
            Long id = savedInstanceState.getLong("id");
            String name = savedInstanceState.getString("name");
            String dateAndTime = savedInstanceState.getString("dateAndTime");
            String guestOfHonour = savedInstanceState.getString("guestOfHonour");
            String desc = savedInstanceState.getString("desc");
            String organizer = savedInstanceState.getString("organizer");
            String contactNo = savedInstanceState.getString("contactNo");
            String location = savedInstanceState.getString("location");

            actionBar.setTitle(name);
            event = new Event(id,name,dateAndTime,guestOfHonour,desc,organizer,contactNo,location);
        }

        // Set up the ViewPager with the sections adapter.
        mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);

        mJazzy.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mJazzy.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ViewEventPagerAdapter(ViewEventActivity.this,getSupportFragmentManager(),event,mJazzy);

        mJazzy.setAdapter(mSectionsPagerAdapter);

        if(CheckNetworkConnection.isNetworkConnectionAvailable(this)) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_info_outline_white_24dp).setTabListener(this));
            actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_place_white_24dp).setTabListener(this));

        }
        else{
            actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_info_outline_white_24dp).setTabListener(this));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mJazzy.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
