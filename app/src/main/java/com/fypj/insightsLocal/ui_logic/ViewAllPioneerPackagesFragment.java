package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.HomeSection;
import com.fypj.insightsLocal.util.HomeListAdapter;
import com.fypj.insightsLocal.util.ViewAllPioneerPackagesListAdapter;

import java.util.ArrayList;

/**
 * Created by jess on 20-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAllPioneerPackagesFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_number";
    MainPageActivity activity;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public ViewAllPioneerPackagesFragment newInstance(MainPageActivity activity,int sectionNumber) {
        ViewAllPioneerPackagesFragment fragment = new ViewAllPioneerPackagesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        this.activity = activity;
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }

    public ViewAllPioneerPackagesFragment() {
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pioneer_packages,menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pioneer_packages, container, false);
        getActivity().getActionBar().setTitle("View Pioneer Packages");

        final ListView lvPackagesList = (ListView) rootView.findViewById(R.id.lv_packages_list);

        final ArrayList<String> packagesArrList = new ArrayList<String>();
        packagesArrList.add("CHAS Blue");
        packagesArrList.add("CHAS Orange");
        packagesArrList.add("CHAS for Pioneer Generation");

        ViewAllPioneerPackagesListAdapter adapter = new ViewAllPioneerPackagesListAdapter(ViewAllPioneerPackagesFragment.this.getActivity(),android.R.id.text1,packagesArrList);
        lvPackagesList.setAdapter(adapter);
        lvPackagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println(lvPackagesList.getItemAtPosition(position));
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainPageActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
