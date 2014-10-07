package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.util.HomeListAdapter;
import com.fypj.insightsLocal.controller.*;

import java.util.ArrayList;

/**
 * This is an fragment class to do the UI logic for displaying the home page screen.
 * Created by Lee Zhuo Xun on 19-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomeFragment extends Fragment {
    private Context activity;
    private int position;
    /**
     * Default constructor
     */
    public HomeFragment() {}

    public HomeFragment newInstance(Context activity,int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("section_number", 1);
        this.activity = activity;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView lvHomeList = (ListView) rootView.findViewById(R.id.lv_home_list);

        final ArrayList<String> homeSectionArrList = new ArrayList<String>();
        homeSectionArrList.add("Pioneer Generation Packages");
        homeSectionArrList.add("Lifestyle Events");
        homeSectionArrList.add("Clinics");

        HomeListAdapter adapter = new HomeListAdapter(HomeFragment.this.getActivity(),android.R.id.text1,homeSectionArrList);
        lvHomeList.setAdapter(adapter);
        lvHomeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    HomeFragment.this.position = 3;
                    onAttach(getActivity());
                }
                if(position == 1){
                    HomeFragment.this.position = 4;
                    onAttach(getActivity());
                }
                if(position == 2){
                    Intent intent = new Intent(HomeFragment.this.getActivity(),NearestClinicActivity.class);
                    intent.putExtra("choice",0);
                    startActivity(intent);
                }
            }
        });
        getActivity().getActionBar().setTitle("Home");
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
