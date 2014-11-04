package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.options.CheckNetworkConnection;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.model.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by jess on 19-Sep-14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ViewAllLatestEventsActivity extends ActionBarActivity {
    private final String ARG_SECTION_NUMBER = "section_num--ber";
    private SwipeRefreshLayout swipeView;
    private ListView lvLatestEvents;
    private ArrayList<Event> latestEventArrList;
    private ArrayList<Event> searchResultsArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_latest_events);
        this.getSupportActionBar().setTitle("Lifestyle Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvLatestEvents = (ListView) findViewById(R.id.lv_latest_events);
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeView.setEnabled(false);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh(true);
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
        if(CheckNetworkConnection.isNetworkConnectionAvailable(this)){

        }
        refresh(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.latest_events, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if(searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //System.out.println("Query: " + query);
                    EventSQLController controller = new EventSQLController(ViewAllLatestEventsActivity.this);
                    searchResultsArrList = controller.searchEvent(query);

                    LatestEventsListAdapter adapter = new LatestEventsListAdapter(ViewAllLatestEventsActivity.this, android.R.id.text1, searchResultsArrList);
                    lvLatestEvents.setAdapter(adapter);

                    lvLatestEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(ViewAllLatestEventsActivity.this, ViewEventActivity.class);
                            intent.putExtra("id", searchResultsArrList.get(position).getEventID());
                            intent.putExtra("name", searchResultsArrList.get(position).getName());
                            intent.putExtra("dateAndTime", searchResultsArrList.get(position).getDateAndTime());
                            intent.putExtra("guestOfHonour", searchResultsArrList.get(position).getGuestOfHonour());
                            intent.putExtra("desc", searchResultsArrList.get(position).getDesc());
                            intent.putExtra("organizer", searchResultsArrList.get(position).getOrganizer());
                            intent.putExtra("contactNo", searchResultsArrList.get(position).getContactNo());
                            intent.putExtra("location", searchResultsArrList.get(position).getLocation());
                            startActivity(intent);
                        }
                    });
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh(boolean refresh){
        EventSQLController controller = new EventSQLController(ViewAllLatestEventsActivity.this);

        latestEventArrList = controller.getAllEvent();

        sortDate();

        LatestEventsListAdapter adapter = new LatestEventsListAdapter(ViewAllLatestEventsActivity.this, android.R.id.text1, latestEventArrList);
        lvLatestEvents.setAdapter(adapter);

        lvLatestEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ViewAllLatestEventsActivity.this, ViewEventActivity.class);
                intent.putExtra("id", latestEventArrList.get(position).getEventID());
                intent.putExtra("name", latestEventArrList.get(position).getName());
                intent.putExtra("dateAndTime", latestEventArrList.get(position).getDateAndTime());
                intent.putExtra("guestOfHonour", latestEventArrList.get(position).getGuestOfHonour());
                intent.putExtra("desc", latestEventArrList.get(position).getDesc());
                intent.putExtra("organizer", latestEventArrList.get(position).getOrganizer());
                intent.putExtra("contactNo", latestEventArrList.get(position).getContactNo());
                intent.putExtra("location", latestEventArrList.get(position).getLocation());
                startActivity(intent);
            }
        });
        if(refresh) {
            swipeView.setRefreshing(false);
        }
    }

    private void sortDate(){

        Collections.sort(latestEventArrList, new Comparator<Event>() {
            public int compare(Event o1, Event o2) {

                DateFormat df = new SimpleDateFormat("dd MMMM yyyy h:mma");
                try {
                    Date dt = df.parse(o1.getDateAndTime().substring(0, o1.getDateAndTime().lastIndexOf("to")));
                    Date dt2 = df.parse(o2.getDateAndTime().substring(0, o2.getDateAndTime().lastIndexOf("to")));
                    if (dt == null || dt2 == null)
                        return 0;
                    return dt.compareTo(dt2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
