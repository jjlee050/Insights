package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.mymodule.api.insightsEvent.model.Event;

import java.util.ArrayList;

/**
 * Created by jess on 20-Sep-14.
 */
public class LatestEventsListAdapter extends ArrayAdapter<Event> {
    private Activity context;
    private ArrayList<Event> eventArrList;

    public LatestEventsListAdapter(Activity context, int textViewResourceId, ArrayList<Event> eventArrList) {
        super(context, R.layout.list_latest_events, eventArrList);
        this.context = (Activity) context;
        this.eventArrList = eventArrList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_latest_events, null, true);

        TextView tvEventName = (TextView) rowView.findViewById(R.id.tv_event_name);
        TextView tvEventDateTime = (TextView) rowView.findViewById(R.id.tv_event_date_time);
        TextView tvEventDesc = (TextView) rowView.findViewById(R.id.tv_event_description);

        System.out.println("Position: " + position);

        tvEventName.setText(eventArrList.get(position).getName());
        tvEventDateTime.setText(eventArrList.get(position).getDateAndTime());
        tvEventDesc.setText(eventArrList.get(position).getDesc());
        return rowView;
    }
}