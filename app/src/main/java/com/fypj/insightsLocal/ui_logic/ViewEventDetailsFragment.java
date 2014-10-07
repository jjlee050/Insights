package com.fypj.insightsLocal.ui_logic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import  com.fypj.insightsLocal.model.Event;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewEventDetailsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */


    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewEventDetailsFragment newInstance(int sectionNumber) {
        ViewEventDetailsFragment fragment = new ViewEventDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewEventDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_event_details, container, false);
        return rootView;
    }


}
