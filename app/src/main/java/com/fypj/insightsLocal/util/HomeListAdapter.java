package com.fypj.insightsLocal.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.HomeSection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_home, null, true);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tvName = (TextView) rowView.findViewById(R.id.tv_name);
        TextView tvContactNo = (TextView) rowView.findViewById(R.id.tv_contact_no);
        ImageView ivClinicImg = (ImageView) rowView.findViewById(R.id.iv_clinic_img);
        ImageView ivImg = (ImageView) rowView.findViewById(R.id.iv_img);

        if(position != 2){
            ivClinicImg.setVisibility(View.VISIBLE);
            if(position == 0){
                ivClinicImg.setImageResource(R.drawable.medical_clinic);
                ivImg.setImageResource(R.drawable.medic);
            }
            else if(position == 1){
                ivClinicImg.setImageResource(R.drawable.dental_clinic);
                ivImg.setImageResource(R.drawable.dental);
            }
        }
        else{
            ivClinicImg.getLayoutParams().width = 35;
        }

        System.out.println("Position: " + position);
        tvTitle.setText(sectionArrList.get(position).getTitle());
        tvName.setText(sectionArrList.get(position).getName());
        tvContactNo.setText(sectionArrList.get(position).getContactNo());


        return rowView;
    }
}
