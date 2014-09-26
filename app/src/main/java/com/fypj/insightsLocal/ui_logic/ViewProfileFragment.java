package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.util.ProfileListAdapter;

import java.util.ArrayList;

/**
 * Created by L33525 on 26/9/2014.
 */
public class ViewProfileFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewProfileFragment newInstance(int sectionNumber) {
        ViewProfileFragment fragment = new ViewProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_profile, container, false);
        ListView lvProfileList = (ListView) rootView.findViewById(R.id.lv_profile_list);
        ArrayList<String> titleArrList = new ArrayList<String>();
        titleArrList.add("Basic Information");
        titleArrList.add("Balance");
        ProfileListAdapter adapter = new ProfileListAdapter(ViewProfileFragment.this.getActivity(),android.R.id.text1,titleArrList);
        lvProfileList.setAdapter(adapter);
        return rootView;
    }
}
