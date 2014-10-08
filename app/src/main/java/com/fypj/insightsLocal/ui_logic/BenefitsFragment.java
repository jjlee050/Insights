package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

import java.util.Locale;

public class BenefitsFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    ViewPioneerPackageActivity activity;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public BenefitsFragment newInstance(ViewPioneerPackageActivity activity,int sectionNumber) {
        BenefitsFragment fragment = new BenefitsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        this.activity = activity;
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }
    public BenefitsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_view_pioneer_package, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tvHeader = (TextView) rootView.findViewById(R.id.tv_header);
        View horizontalLine = rootView.findViewById(R.id.horizontal_line);
        TextView tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);

        tvTitle.setText("Benefits for Pioneer Generation Package");
        tvHeader.setText("The package will help Pioneers with their healthcare costs for life. The benefits are as below: ");
        tvContent.setText(
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
                        );


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

        /*tvContent.setText("The Government has introduced the Pioneer Generation Package to honour and thank our pioneers for their hard work and dedication. They have made Singapore what it is today.\n" +
                "\n" +
                "About 450,000 Singaporeans will benefit from the Pioneer Generation Package.");*/
        horizontalLine.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvHeader.setVisibility(View.GONE);
        tvContent.setVisibility(View.VISIBLE);
        ivImg.setVisibility(View.VISIBLE);

        return rootView;
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
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
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




    }

}
