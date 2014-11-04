package com.fypj.insightsLocal.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fypj.insightsLocal.R;

import java.text.RuleBasedCollator;
import java.util.ArrayList;

/**
 * Created by L33525 on 22/9/2014.
 */
public class HomeListAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> sectionArrList;

    public HomeListAdapter(Activity context, int textViewResourceId, ArrayList<String> sectionArrList) {
        super(context, R.layout.list_latest_events, sectionArrList);
        this.context = (Activity) context;
        this.sectionArrList = sectionArrList;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_home, null, true);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
        ImageView ivClinicImg = (ImageView) rowView.findViewById(R.id.iv_clinic_img);

        if(position == 0){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,60,0,0);
            params.addRule(RelativeLayout.RIGHT_OF,ivClinicImg.getId());
            tvTitle.setLayoutParams(params);
            ivClinicImg.setImageResource(R.drawable.pioneer_generation_pic);
        }
        else if(position == 1){
            ivClinicImg.setImageResource(R.drawable.lifestyle_events);
        }
        else if(position == 2){
            ivClinicImg.setImageResource(R.drawable.chas_clinics_locator);
        }

        System.out.println("Position: " + position);
        tvTitle.setText(sectionArrList.get(position));


        return rowView;
    }
}
