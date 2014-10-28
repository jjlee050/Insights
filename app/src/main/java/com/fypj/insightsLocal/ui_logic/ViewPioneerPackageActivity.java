package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.fypj.insightsLocal.R;
import com.fypj.mymodule.api.insightsPackages.model.Packages;

public class ViewPioneerPackageActivity extends ActionBarActivity implements ActionBar.OnNavigationListener{

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private String[] titles = new String[] {"Overview","Benefits","Subsidies","Eligibility"};
    private int currentIndex = 0;
    private Packages packages = new Packages();

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pioneer_package);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        // Show the Up button in the action bar.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        titles),
                this);

        savedInstanceState = getIntent().getExtras();
        if(savedInstanceState != null){
            Long packagesID = savedInstanceState.getLong("packagesID");
            String name = savedInstanceState.getString("name");
            String overview = savedInstanceState.getString("overview");
            String benefits = savedInstanceState.getString("benefits");
            String eligible = savedInstanceState.getString("eligible");

            packages.setPackageID(packagesID);
            packages.setName(name);
            packages.setOverview(overview);
            packages.setBenefits(benefits);
            packages.setEligible(eligible);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_pioneer_package, menu);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        FragmentManager fragmentManager = getFragmentManager();

        switch(position) {
            case 0:
                OverviewFragment overviewFragment = new OverviewFragment();
                if (currentIndex < position){
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.right_in, R.animator.right_out,
                                    R.animator.left_in, R.animator.left_out)
                            .replace(R.id.container, overviewFragment.newInstance(this, position + 1, packages.getName(), packages.getOverview()))
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.left_in, R.animator.left_out,
                                    R.animator.right_in, R.animator.right_out)
                            .replace(R.id.container, overviewFragment.newInstance(this, position + 1, packages.getName(), packages.getOverview()))
                            .addToBackStack(null)
                            .commit();
                }
                currentIndex = 0;
                break;
            case 1:
                BenefitsFragment benefitsFragment = new BenefitsFragment();
                if (currentIndex < position) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.right_in, R.animator.right_out,
                                    R.animator.left_in, R.animator.left_out)
                            .replace(R.id.container, benefitsFragment.newInstance(this, position + 1, packages.getName(), packages.getBenefits()))
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.left_in, R.animator.left_out,
                                    R.animator.right_in, R.animator.right_out)
                            .replace(R.id.container, benefitsFragment.newInstance(this, position + 1, packages.getName(), packages.getBenefits()))
                            .addToBackStack(null)
                            .commit();
                }
                currentIndex = 1;
                break;
            case 2:
                SubsidiesFragment subsidiesFragment = new SubsidiesFragment();
                if (currentIndex < position) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.right_in, R.animator.right_out,
                                    R.animator.left_in, R.animator.left_out)
                            .replace(R.id.container, subsidiesFragment.newInstance(this, position + 1, packages.getName(), packages.getPackageID()))
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.left_in, R.animator.left_out,
                                    R.animator.right_in, R.animator.right_out)
                            .replace(R.id.container, subsidiesFragment.newInstance(this, position + 1, packages.getName(), packages.getPackageID()))
                            .addToBackStack(null)
                            .commit();
                }
                currentIndex = 2;
                break;
            case 3:
                EligibilityFragment eligibilityFragment = new EligibilityFragment();
                if (currentIndex < position) {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.right_in, R.animator.right_out,
                                    R.animator.left_in, R.animator.left_out)
                            .replace(R.id.container, eligibilityFragment.newInstance(this, position + 1, packages.getName(), packages.getEligible()))
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.animator.left_in, R.animator.left_in,
                                    R.animator.right_in, R.animator.right_out)
                            .replace(R.id.container, eligibilityFragment.newInstance(this, position + 1, packages.getName(), packages.getEligible()))
                            .addToBackStack(null)
                            .commit();
                }
                currentIndex = 3;
                break;

        }

        return false;

    }
}
