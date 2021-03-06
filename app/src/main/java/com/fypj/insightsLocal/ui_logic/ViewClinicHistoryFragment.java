package com.fypj.insightsLocal.ui_logic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetMedicalHistory;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.ClinicSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserMedicalHistoriesSQLController;
import com.fypj.insightsLocal.util.ClinicHistoryListAdapter;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.fypj.mymodule.api.myMedicalHistory.model.MedicalHistory;

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
    /**h
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

        UserMedicalHistoriesSQLController controller = new UserMedicalHistoriesSQLController(ViewClinicHistoryFragment.this.getActivity());
        ArrayList<MedicalHistory> myMedicalHistoriesArrList = controller.getMedicalHistoryByNRIC(nric);
        ArrayList<String> clinicNameArrList = new ArrayList<String>();
        for(int i=0;i<myMedicalHistoriesArrList.size();i++){
            Long clinicID = myMedicalHistoriesArrList.get(i).getClinicID();
            ClinicSQLController clinicController = new ClinicSQLController(ViewClinicHistoryFragment.this.getActivity());
            clinicNameArrList.add(clinicController.getClinic(clinicID).getName());
        }

        ClinicHistoryListAdapter adapter = new ClinicHistoryListAdapter(ViewClinicHistoryFragment.this,android.R.id.text1,myMedicalHistoriesArrList,clinicNameArrList);
        lvClinicHistoryList.setAdapter(adapter);

        return rootView;
    }
}
