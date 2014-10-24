package com.fypj.insightsLocal.ui_logic;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
            String name = savedInstanceState.getString("name");
            String overview = savedInstanceState.getString("overview");
            String benefits = savedInstanceState.getString("benefits");
            String eligible = savedInstanceState.getString("eligible");

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

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        FragmentManager fragmentManager = getFragmentManager();
        switch(position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(this, position + 1, packages.getName(), packages.getOverview()))
                        .commit();
                currentIndex = 0;
                break;
            case 1:
                BenefitsFragment benefitsFragment = new BenefitsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, benefitsFragment.newInstance(this, position + 1, packages.getName(), packages.getBenefits()))
                        .commit();
                currentIndex = 1;
                break;
            case 2:
                SubsidesFragment subsidiesFragment = new SubsidesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, subsidiesFragment.newInstance(this, position + 1))
                        .commit();
                currentIndex = 2;
                break;
            case 3:
                EligibilityFragment eligibilityFragment = new EligibilityFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, eligibilityFragment.newInstance(this, position + 1, packages.getName(), packages.getEligible()))
                        .commit();
                currentIndex = 3;
                break;

        }

        return true;

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
        public static PlaceholderFragment newInstance(ViewPioneerPackageActivity viewPioneerPackageActivity, int sectionNumber, String name, String overview) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("name",name);
            args.putString("overview",overview);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_pioneer_package, container, false);
            TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            TextView tvHeader = (TextView) rootView.findViewById(R.id.tv_header);
            View horizontalLine = rootView.findViewById(R.id.horizontal_line);
            TextView tvContent = (TextView) rootView.findViewById(R.id.tv_content);
            ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);

            Bundle bundle = getArguments();

            tvTitle.setText(bundle.getString("name"));
            tvHeader.setText("The package will help Pioneers with their healthcare costs for life. The benefits are as below: ");

            tvContent.setText(Html.fromHtml(bundle.getString("overview")));

            horizontalLine.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
            tvHeader.setVisibility(View.GONE);
            tvContent.setVisibility(View.VISIBLE);
            ivImg.setVisibility(View.VISIBLE);

            return rootView;
        }
    }
}
