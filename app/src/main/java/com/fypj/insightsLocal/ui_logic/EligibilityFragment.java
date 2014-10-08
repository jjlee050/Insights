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

public class EligibilityFragment extends Fragment {
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

    public EligibilityFragment newInstance(ViewPioneerPackageActivity activity,int sectionNumber) {
        EligibilityFragment fragment = new EligibilityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 3);
        this.activity = activity;
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }
    public EligibilityFragment() {
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
        TextView tvContent1 = (TextView) rootView.findViewById(R.id.tv_content1);
        ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);

        tvTitle.setText("Eligibility for Pioneer Generation Package");
        tvHeader.setText("The package will help Pioneers with their healthcare costs for life. The benefits are as below: ");


            tvContent.setText("In order to apply CHAS for Pioneer Generation, Living Singapore Citizens who meet 2 criteria:\n" +
                    "\n\n" +
                    "1.Aged 16 and above in 1965 - this means:\n" +
                    " a.Born on or before 31 December 1949\n" +
                    " b.Aged 65 and above in 2014\n\n" +
                    "2. Obtained citizenship on or before 31 December 1986.\n\n\n");
             tvContent1.setText("Those eligible for the Pioneer Generation Package would have received a notification letter in June 2014. Please keep your NRIC address updated.\n\n" +
                    "For more information: visit http://www.cpf.gov.sg/pioneers/pgp_Faq.asp");

        /*tvContent.setText("The Government has introduced the Pioneer Generation Package to honour and thank our pioneers for their hard work and dedication. They have made Singapore what it is today.\n" +
                "\n" +
                "About 450,000 Singaporeans will benefit from the Pioneer Generation Package.");*/
        horizontalLine.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvHeader.setVisibility(View.GONE);
        tvContent.setVisibility(View.VISIBLE);
        tvContent1.setVisibility(View.VISIBLE);
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
