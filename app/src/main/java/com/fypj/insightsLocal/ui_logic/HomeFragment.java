package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.HomeSection;
import com.fypj.insightsLocal.util.HomeListAdapter;

import java.util.ArrayList;

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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final ListView lvHomeList = (ListView) rootView.findViewById(R.id.lv_home_list);

        final ArrayList<HomeSection> homeSectionArrList = new ArrayList<HomeSection>();
        homeSectionArrList.add(new HomeSection("Nearest CHAS-able Medical Clinic","338 Family Clinic Pte Ltd","Mon, Tue & Thu:\t0830 - 1230; 1400 - 1630; 1900 - 2100\nWed:\t0830 - 1230; 1400 - 1630\nFri, Sat & Sun:\t0830 - 1230"));
        homeSectionArrList.add(new HomeSection("Nearest CHAS-able Dental Clinic","Vista Dental Surgery (ADM) Pte Ltd","678A Woodlands Avenue 6, #01 - 43, \nSingapore - 731678"));
        homeSectionArrList.add(new HomeSection("Upcoming Events","A Talk on An Insight to the Banjarese Community in the Cosmopolitan Society of Singapore",""));

        HomeListAdapter adapter = new HomeListAdapter(HomeFragment.this.getActivity(),android.R.id.text1,homeSectionArrList);
        lvHomeList.setAdapter(adapter);

        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);

        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeView.setEnabled(false);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);

                    }
                }, 3000);
            }
        });

        lvHomeList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeView.setEnabled(true);
                else
                    swipeView.setEnabled(false);
            }
        });
        lvHomeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    Intent intent = new Intent(HomeFragment.this.getActivity(),NearestClinicActivity.class);
                    intent.putExtra("choice",position);
                    startActivity(intent);
                }
                if(position == 1){
                    Intent intent = new Intent(HomeFragment.this.getActivity(),NearestClinicActivity.class);
                    intent.putExtra("choice",position);
                    startActivity(intent);
                }
                if(position == 2){
                    view.setSelected(true);
                    Intent intent = new Intent(HomeFragment.this.getActivity(),ViewEventActivity.class);
                    intent.putExtra("eventID", position);
                    intent.putExtra("eventName",homeSectionArrList.get(position).getName());
                    intent.putExtra("eventDateTime",homeSectionArrList.get(position).getTitle());
                    intent.putExtra("eventDesc",homeSectionArrList.get(position).getContactNo());

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
        ((MainPageActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
