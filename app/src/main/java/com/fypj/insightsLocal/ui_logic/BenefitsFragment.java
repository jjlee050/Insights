package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
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
        View rootView = inflater.inflate(R.layout.fragment_view_pg_benefits, container, false);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tvHeader = (TextView) rootView.findViewById(R.id.tv_header);
        View horizontalLine = rootView.findViewById(R.id.horizontal_line);
        TextView tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        TextView tvContent1 = (TextView) rootView.findViewById(R.id.tv_content1);
        TextView tvContent3 = (TextView) rootView.findViewById(R.id.tv_content3);
        TextView tvAge = (TextView) rootView.findViewById(R.id.tv_age);
        TextView tvInfo = (TextView) rootView.findViewById(R.id.tv_info);
        TextView tvAge1 = (TextView) rootView.findViewById(R.id.tv_age1);
        TextView tvInfo1 = (TextView) rootView.findViewById(R.id.tv_info1);
        TextView tvExtra = (TextView) rootView.findViewById(R.id.tv_extra);
        TextView tvTitle1 = (TextView) rootView.findViewById(R.id.tv_title1);
        TextView tvTitle2 = (TextView) rootView.findViewById(R.id.tv_title2);
        ImageView ivImg = (ImageView) rootView.findViewById(R.id.iv_image);

        tvTitle.setText("Pioneer Generation Package Benefits");
        tvTitle2.setText(Html.fromHtml("<u> OutPatient Care </u>"));
        tvHeader.setText("The package will help Pioneers with their healthcare costs for life. The benefits are as below: ");
        tvContent.setText(Html.fromHtml("<b> Additional 50 % off subsidised services </b> at polyclinics and Specialist Outpatient Clinics."));
        tvContent1.setText(Html.fromHtml("<b> Additional 50% off subsidised medications </b> at polyclinics and Specialist Outpatient Clinics."));
        tvTitle1.setText(Html.fromHtml("<u>MediShield Life</u>"));
        tvContent3.setText("Support for all Pioneers’ MediShield Life Premiums with special premium subsidies and Medisave top-ups.\n");
        tvAge.setText("Age 80 and above* : ");
        tvInfo.setText(Html.fromHtml("<b> Premiums fully covered </b>"));
        tvAge1.setText("Age 65 to 79* : ");
        tvInfo1.setText(Html.fromHtml("<b> Pay half of current premiums </b>"));
        tvExtra.setText("*Age in 2014");

        horizontalLine.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        tvHeader.setVisibility(View.GONE);
        tvContent.setVisibility(View.VISIBLE);
        tvContent1.setVisibility(View.VISIBLE);
        tvContent3.setVisibility(View.VISIBLE);
        tvAge.setVisibility(View.VISIBLE);
        tvInfo.setVisibility(View.VISIBLE);
        tvAge1.setVisibility(View.VISIBLE);
        tvInfo1.setVisibility(View.VISIBLE);
        tvExtra.setVisibility(View.VISIBLE);
        tvTitle1.setVisibility(View.VISIBLE);
        tvTitle2.setVisibility(View.VISIBLE);
        ivImg.setVisibility(View.VISIBLE);

        return rootView;
    }
}
