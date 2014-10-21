package com.fypj.insightsLocal.ui_logic;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

/**
 * Created by L33524 on 22/9/2014.
 */
public class ViewClinicDetailsFragment extends Fragment{

    private final String ARG_SECTION_NUMBER = "section_number";
    ViewClinicActivity activity;

    private TextView tvClinicName;
    private TextView tvClinicAddress;
    private TextView tvClinicOH;
    private TextView tvClinicContactNo;

    public ViewClinicDetailsFragment newInstance(ViewClinicActivity activity,int sectionNumber) {
        ViewClinicDetailsFragment fragment = new ViewClinicDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        this.activity = activity;
        fragment.setArguments(args);
        return fragment;
    }

    public ViewClinicDetailsFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_clinic_details, container, false);
        Bundle bundle = getArguments();

        tvClinicName = (TextView) rootView.findViewById(R.id.tv_Clinic_name);
        tvClinicAddress = (TextView) rootView.findViewById(R.id.tv_Clinic_Address);
        tvClinicOH = (TextView) rootView.findViewById(R.id.tv_ClinicOH);
        tvClinicContactNo = (TextView) rootView.findViewById(R.id.tv_Clinic_Contact);


        tvClinicName.setText(bundle.getString("ClinicName"));
        tvClinicAddress.setText(bundle.getString("ClinicAddress"));
        tvClinicOH.setText(bundle.getString("ClinicOH"));
        tvClinicContactNo.setText(bundle.getString("ClinicContactNo"));



        return rootView;
    }





}
