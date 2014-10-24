package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public EligibilityFragment newInstance(ViewPioneerPackageActivity activity,int sectionNumber, String name, String eligible) {
        EligibilityFragment fragment = new EligibilityFragment();
        Bundle args = new Bundle();
        args.putString("name",name);
        args.putString("eligible",eligible);
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
        View rootView = inflater.inflate(R.layout.fragment_view_pg_eligibility, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);

        Bundle bundle = getArguments();

        tvTitle.setText(Html.fromHtml("<p>Eligibility Criteria</p>"));

        tvContent.setText(Html.fromHtml(bundle.getString("eligible")));

        return rootView;
    }
}
