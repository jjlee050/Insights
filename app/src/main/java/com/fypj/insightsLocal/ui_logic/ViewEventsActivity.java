package com.fypj.insightsLocal.ui_logic;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

public class ViewEventsActivity extends ActionBarActivity {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        savedInstanceState = getIntent().getExtras();


        TextView tvEventName = (TextView) findViewById(R.id.tv_event_name);
        TextView tvEventDateTime = (TextView) findViewById(R.id.tv_event_date_time);
        TextView tvEventDesc = (TextView) findViewById(R.id.tv_event_description);

        if(savedInstanceState != null){
            String eventName = savedInstanceState.getString("eventName");
            String eventDateTime = savedInstanceState.getString("eventDateTime");
            String eventDesc = savedInstanceState.getString("eventDesc");

            tvEventName.setText(eventName);
            getActionBar().setTitle(eventName);
            tvEventDateTime.setText(eventDateTime);
            tvEventDesc.setText(eventDesc);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_events, menu);
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
