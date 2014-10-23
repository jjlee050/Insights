package com.fypj.insightsLocal.ui_logic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetUserMedicalHistory;
import com.fypj.insightsLocal.model.Clinic;
import com.fypj.insightsLocal.model.MedicalHistory;
import com.fypj.insightsLocal.util.ClinicHistoryListAdapter;

import java.util.ArrayList;

/**
 * Created by L33525 on 26/9/2014.
 */
public class ViewClinicHistoryFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewClinicHistoryFragment newInstance(int sectionNumber) {
        ViewClinicHistoryFragment fragment = new ViewClinicHistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewClinicHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_clinic_history, container, false);
        ListView lvClinicHistoryList = (ListView) rootView.findViewById(R.id.lv_clinic_history_list);

        SharedPreferences sharedPref= ViewClinicHistoryFragment.this.getActivity().getSharedPreferences("insightsPreferences", 0);
        String nric = sharedPref.getString("nric", "");
        new GetUserMedicalHistory(ViewClinicHistoryFragment.this.getActivity(),nric,lvClinicHistoryList).execute();
        return rootView;
    }
}
