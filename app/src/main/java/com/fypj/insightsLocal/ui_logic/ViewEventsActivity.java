package com.fypj.insightsLocal.ui_logic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

public class ViewEventsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        savedInstanceState = getIntent().getExtras();


        TextView tvEventName = (TextView) findViewById(R.id.tv_event_name);
        TextView tvEventDateTime = (TextView) findViewById(R.id.tv_event_date_time);
        TextView tvEventDesc = (TextView) findViewById(R.id.tv_event_description);

        if(savedInstanceState != null){
            tvEventName.setText(savedInstanceState.getString("eventName"));
            tvEventDateTime.setText(savedInstanceState.getString("eventDateTime"));
            tvEventDesc.setText(savedInstanceState.getString("eventDesc"));
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
