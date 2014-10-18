package com.fypj.insightsLocal.ui_logic;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.EventSQLController;
import com.fypj.insightsLocal.util.LatestEventsListAdapter;
import com.fypj.mymodule.api.insightsEvent.model.Event;

import java.util.ArrayList;

public class ViewEventSearchResultsActivity extends ActionBarActivity {
    private ListView lvEvents;
    private ArrayList<Event> searchResultArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_search_results);
        lvEvents = (ListView) findViewById(R.id.lv_latest_events);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);
            /**
             * Use this query to display search results like
             * 1. Getting the data from SQLite and showing in listview
             * 2. Making webrequest and displaying the data
             * For now we just display the query only
             */
            //txtQuery.setText("Search Query: " + query);

            EventSQLController controller = new EventSQLController(ViewEventSearchResultsActivity.this);
            searchResultArrList = controller.getAllEvent();
            System.out.println("Size: " + searchResultArrList.size());
            for(int i=0;i<)
            LatestEventsListAdapter adapter = new LatestEventsListAdapter(ViewEventSearchResultsActivity.this, android.R.id.text1, searchResultArrList);

            lvEvents.setAdapter(adapter);

            lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ViewEventSearchResultsActivity.this, ViewEventActivity.class);
                    intent.putExtra("id", searchResultArrList.get(position).getEventID());
                    intent.putExtra("name", searchResultArrList.get(position).getName());
                    intent.putExtra("dateAndTime", searchResultArrList.get(position).getDateAndTime());
                    intent.putExtra("guestOfHonour", searchResultArrList.get(position).getGuestOfHonour());
                    intent.putExtra("desc", searchResultArrList.get(position).getDesc());
                    intent.putExtra("organizer", searchResultArrList.get(position).getOrganizer());
                    intent.putExtra("contactNo", searchResultArrList.get(position).getContactNo());
                    intent.putExtra("location", searchResultArrList.get(position).getLocation());
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_event_search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
