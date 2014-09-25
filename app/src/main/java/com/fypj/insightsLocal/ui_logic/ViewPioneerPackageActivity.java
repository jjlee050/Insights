package com.fypj.insightsLocal.ui_logic;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fypj.insightsLocal.R;

import java.util.ArrayList;

public class ViewPioneerPackageActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

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
                        new String[] {
                                "Cover Page",
                                "Overview",
                                "Benefits",
                                "Subsidies",
                                "Eligibility",
                        }),
                this);
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
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
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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

            tvTitle.setText("CHAS for Pioneer Generation");
            tvHeader.setText("The package will help Pioneers with their healthcare costs for life. The benefits are as below: ");
            /*tvContent.setText(
                        "Additional 50% off subsidised services at polyclinics and Specialist Outpatient Clinics (Sep 2014).\n\n" +
                        "Additional 50% off subsidised medications at polyclinics and Specialist Outpatient Clinics (Jan 2015).\n\n" +
                        "Additional 50% off the bill on Subsidy for Pioneers for SOC & Polyclinic services(From 1 Sep 2014)\n" +"and\n" + "subsidised medication at SOC and Polyclinic (From 1 Jan 2015).\n\n" +
                        "Pioneers from lower- to middle-income households can also enjoy these higher subsidies, plus the additional Pioneer benefits of \"50% off\" their subsidised bill.\n\n" +
                        "Enjoy subsidies at participating GP and dental clinics under the Community Health Assist Scheme (CHAS). Do remember to bring your Pioneer Generation card along when visiting a CHAS clinic.\n\n" +
                        "Cash of $1,200 a year for those with moderate to severe functional disabilities under the Pioneer Generation Disability Assistance Scheme. \n\n" +
                        "$200 to $800 annually for life. \n\n" +
                        "Support for all Pioneers’ MediShield Life Premiums with special premium subsidies and Medisave top-ups.\n" +
                        " -  Aged 80 and above in 2014: Premiums fully covered\n" +
                        " -  Aged 65 to 79 and fully insured under MediShield today: Pay half of current premiums.\n\n"+
                        "All Pioneers will pay less for MediShield Life premiums than today. "
                        );*/

            /*tvContent.setText(
                    "Common illnesses (e.g. cough and cold):\n\n$28.50\n\n" +
                            "\n\n" +
                    "Simple Chronic conditions" +
                            "under CDMP:\n\n$90 per visit, capped at $360 per year\n\n" +
                            "\n\n" +
                    "Complex Chronic conditions" +
                            "under CDMP:\n" +
                            "\n$135 per visit, capped at $540 per year\n\n\n" +
                            "\n" +
                    "Selected dental services:\n" +
                            "\n$21 to $266.50 per procedure (dependent on procedure)\n\n" +
                            "\n\n" +
                    "Health screening under " +
                            "HPB’s ISP4:\n" +
                            "\nScreening tests: Free with HPB's invitation letter; and Doctor's consultation: $28.50 per visit (up to 2 times per year)");*/

            /*tvContent.setText("In order to apply CHAS for Pioneer Generation, Living Singapore Citizens who meet 2 criteria:\n" +
                    "\n\n" +
                    "Aged 16 and above in 1965 - this means:\n" +
                    "1a. Born on or before 31 December 1949\n" +
                    "1b. Aged 65 and above in 2014\n\n" +
                    "and\n" +
                    "\n" +
                    "2. Obtained citizenship on or before 31 December 1986.\n\n\n" +
                    "Those eligible for the Pioneer Generation Package would have received a notification letter in June 2014. Please keep your NRIC address updated.\n\n\n\n\n" +
                    "For more information: visit http://www.cpf.gov.sg/pioneers/pgp_Faq.asp");*/

            tvContent.setText("The Government has introduced the Pioneer Generation Package to honour and thank our pioneers for their hard work and dedication. They have made Singapore what it is today.\n" +
                    "\n" +
                    "About 450,000 Singaporeans will benefit from the Pioneer Generation Package.");

            horizontalLine.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
            tvHeader.setVisibility(View.GONE);
            tvContent.setVisibility(View.VISIBLE);
            ivImg.setVisibility(View.VISIBLE);

            return rootView;
        }
    }

}
