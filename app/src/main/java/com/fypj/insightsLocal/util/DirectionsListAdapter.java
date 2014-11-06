package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.google_directions.Segment;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;

import java.text.DecimalFormat;
import java.util.ArrayList;

//import com.fypj.insightsLocal.model.Clinic;

/**
 * Created by L33524 on 22/9/2014.
 */
public class DirectionsListAdapter extends ArrayAdapter<Segment> {
    private Activity context;
    private ArrayList<Segment> segmentArrayList;

    public DirectionsListAdapter(Activity context, int textViewResourceId, ArrayList<Segment> segmentArrayList) {
        super(context, R.layout.list_directions, segmentArrayList);
        this.context = (Activity) context;
        this.segmentArrayList = segmentArrayList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_directions, null, true);

        TextView tvInstructions = (TextView) rowView.findViewById(R.id.tv_instructions);
        TextView tvDistance = (TextView) rowView.findViewById(R.id.tv_distance);
        TextView tvDuration = (TextView) rowView.findViewById(R.id.tv_duration);
        ImageView ivMode= (ImageView) rowView.findViewById(R.id.iv_mode);
        if(segmentArrayList.get(position).getMode().equals("WALKING")){
            ivMode.setImageResource(R.drawable.walk_mode);
        }
        else if(segmentArrayList.get(position).getMode().equals("TRANSIT")){
            ivMode.setImageResource(R.drawable.transit_mode);
        }
        else if(segmentArrayList.get(position).getMode().equals("DRIVING")){
            ivMode.setImageResource(R.drawable.driving);
        }
        double length = segmentArrayList.get(position).getLength() / 1000.00000;
        DecimalFormat formatter = new DecimalFormat("0.00 mi left");

        int duration = segmentArrayList.get(position).getDuration();
        String durationText = "";
        if(duration >= 3600){
            double hours = duration/3600.00;
            DecimalFormat formatterHrs = new DecimalFormat("0.00");
            durationText = formatterHrs.format(hours)  + " hrs";

        }
        else if((duration > 60) && (duration < 3600)){
            int mins = duration/60;
            int sec = duration % 60;
            //DecimalFormat formatterSec = new DecimalFormat("0.00");
            durationText = mins  + " mins " + sec + " secs";

        }
        else{
            int secs = duration;
            durationText = secs + "secs";
        }
        DecimalFormat formatter2 = new DecimalFormat("0.00 mi left");

        //System.out.println(segmentArrayList.get(position).getMode());
        tvInstructions.setText(segmentArrayList.get(position).getInstruction());
        tvDistance.setText(formatter.format(length));
        tvDuration.setText(durationText);
        return rowView;
    }
}
