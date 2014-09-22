package com.fypj.insightsLocal.ui_logic;

import android.app.Activity;
import android.app.Fragment;
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
    MainPageActivity activity;

    public ViewClinicDetailsFragment newInstance(MainPageActivity activity,int sectionNumber) {
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView tvSection = (TextView) rootView.findViewById(R.id.section_label);
        tvSection.setText("View Nearest Clinic Details");
        getActivity().getActionBar().setTitle("View Nearest Clinic Details");
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainPageActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


}
