package com.fypj.insightsLocal.ui_logic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
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
        /*ListView lvClinicHistoryList = (ListView) rootView.findViewById(R.id.lv_clinic_history_list);
        ArrayList<ClinicHistory> clinicHistoryArrayList = new ArrayList<ClinicHistory>();
        clinicHistoryArrayList.add(new ClinicHistory("9/5/14 at 338 Family Clinic","Flu",24));
        clinicHistoryArrayList.add(new ClinicHistory("26/9/14 at 338 Family Clinic","Fever",28));
        ClinicHistoryListAdapter adapter = new ClinicHistoryListAdapter(ViewClinicHistoryFragment.this.getActivity(),android.R.id.text1,clinicHistoryArrayList);
        lvClinicHistoryList.setAdapter(adapter);*/
        return rootView;
    }
}
