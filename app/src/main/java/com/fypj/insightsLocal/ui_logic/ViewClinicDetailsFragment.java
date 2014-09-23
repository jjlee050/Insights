package com.fypj.insightsLocal.ui_logic;

import android.app.Activity;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_clinic_details, container, false);

        return rootView;
    }





}
