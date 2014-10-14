package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.controller.GetEvents;

import com.fypj.insightsLocal.util.LatestEventsListAdapter;

import java.util.ArrayList;

/**
 * Created by jess on 19-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAllLatestEventsFragment extends Fragment {
    private final String ARG_SECTION_NUMBER = "section_num--ber";
    private SwipeRefreshLayout swipeView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public Fragment newInstance(int sectionNumber) {
        ViewAllLatestEventsFragment fragment = new ViewAllLatestEventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, 1);
        fragment.setArguments(args);
        setHasOptionsMenu(true);
        return fragment;
    }

    public SwipeRefreshLayout getSwipeView() {
        return swipeView;
    }

    public void setSwipeView(SwipeRefreshLayout swipeView) {
        this.swipeView = swipeView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.latest_events,menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_all_latest_events, container, false);
        getActivity().getActionBar().setTitle("Lifestyle Events");
        final ListView lvLatestEvents = (ListView) rootView.findViewById(R.id.lv_latest_events);

        swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);

        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeView.setEnabled(false);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAllEvents(lvLatestEvents);
                    }
                }, 1000);
            }
        });

        lvLatestEvents.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        getAllEvents(lvLatestEvents);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainPageActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private void getAllEvents(ListView lvLatestEvents){
        new GetEvents(ViewAllLatestEventsFragment.this.getActivity(),lvLatestEvents,swipeView).execute();
    }
}
