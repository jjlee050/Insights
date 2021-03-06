package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
//import com.fypj.insightsLocal.model.Clinic;
import com.fypj.insightsLocal.ui_logic.NearestClinicActivity;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;


import java.util.ArrayList;

/**
 * Created by L33524 on 22/9/2014.
 */
public class ClinicAdapter extends ArrayAdapter<Clinic> {
    private Activity context;
    private ArrayList<Clinic> ClinicArrList;

    public ClinicAdapter(Activity context, int textViewResourceId, ArrayList<Clinic> ClinicArrList) {
        super(context, R.layout.list_clinic, ClinicArrList);
        this.context = (Activity) context;
        this.ClinicArrList = ClinicArrList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_clinic, null, true);

        TextView tvClinicName = (TextView) rowView.findViewById(R.id.tv_Clinic_name);
        TextView tvClinicOH = (TextView) rowView.findViewById(R.id.tv_Clinic_OH);
        ImageView ivImg = (ImageView) rowView.findViewById(R.id.iv_img);

        if(position != 4){
            ivImg.setVisibility(View.VISIBLE);
            if(position == 0){
                ivImg.setImageResource(R.drawable.pic_medical);

            }
            else if(position == 1){
                ivImg.setImageResource(R.drawable.pic_medical);

            }
            else if(position == 2){
                ivImg.setImageResource(R.drawable.pic_medical);

            }
            else if(position == 3){
                ivImg.setImageResource(R.drawable.pic_medical);

            }

        }



        System.out.println("Position: " + position);

        tvClinicName.setText(ClinicArrList.get(position).getName());
        tvClinicOH.setText(ClinicArrList.get(position).getOperatingHours());
        return rowView;
    }


}
