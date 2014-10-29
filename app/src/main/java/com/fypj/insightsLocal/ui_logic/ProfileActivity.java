package com.fypj.insightsLocal.ui_logic;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.insightsLocal.util.ProfilePagerAdapter;
import com.fypj.insightsLocal.util.ViewEventPagerAdapter;
import com.fypj.mymodule.api.insightsUser.model.User;
import com.jfeinstein.jazzyviewpager.JazzyViewPager;

public class ProfileActivity extends ActionBarActivity implements ActionBar.TabListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    ProfilePagerAdapter mSectionsPagerAdapter;
    JazzyViewPager mJazzy;
    User user;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        getData();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager(),mJazzy,user);

        mJazzy.setAdapter(mSectionsPagerAdapter);


        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_action_about).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_action_collection).setTabListener(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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
        else if( id == R.id.check_eligibility){
            Intent intent = new Intent(ProfileActivity.this, CheckEligibilityActivity.class);
            startActivity(intent);
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

    public void getData(){
        SharedPreferences sharedPref= getSharedPreferences("insightsPreferences", 0);
        String nric = sharedPref.getString("nric", "");
        if(!nric.equals("")){
            UserSQLController controller = new UserSQLController(this);
            User user = controller.getUser(nric);
            getSupportActionBar().setTitle(user.getName());
            this.user = user;
        }
    }
}
