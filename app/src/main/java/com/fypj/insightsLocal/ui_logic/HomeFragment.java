package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

/**
 * Created by jess on 19-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomeFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    MainPageActivity activity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public Fragment newInstance(MainPageActivity activity,int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        this.activity = activity;
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
        TextView tvSection = (TextView) rootView.findViewById(R.id.section_label);
        tvSection.setText("Home");
        getActivity().getActionBar().setTitle("Home");
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainPageActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
