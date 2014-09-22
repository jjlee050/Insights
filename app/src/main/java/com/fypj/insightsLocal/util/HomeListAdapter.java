package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.HomeSection;

import java.util.ArrayList;

/**
 * Created by L33525 on 22/9/2014.
 */
public class HomeListAdapter extends ArrayAdapter<HomeSection> {
    private Activity context;
    private ArrayList<HomeSection> sectionArrList;

    public HomeListAdapter(Activity context, int textViewResourceId, ArrayList<HomeSection> sectionArrList) {
        super(context, R.layout.list_latest_events, sectionArrList);
        this.context = (Activity) context;
        this.sectionArrList = sectionArrList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_home, null, true);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tvName = (TextView) rowView.findViewById(R.id.tv_name);
        TextView tvLocation = (TextView) rowView.findViewById(R.id.tv_location);

        System.out.println("Position: " + position);
        tvTitle.setText(sectionArrList.get(position).getTitle());
        tvName.setText(sectionArrList.get(position).getName());
        tvLocation.setText(sectionArrList.get(position).getLocation());
        return rowView;
    }
}
