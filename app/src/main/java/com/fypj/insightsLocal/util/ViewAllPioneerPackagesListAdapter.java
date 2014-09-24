package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.Event;

import java.util.ArrayList;

/**
 * Created by L33525 on 24/9/2014.
 */
public class ViewAllPioneerPackagesListAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> packagesArrList;

    public ViewAllPioneerPackagesListAdapter(Activity context, int textViewResourceId, ArrayList<String> packagesArrList) {
        super(context, R.layout.list_packages, packagesArrList);
        this.context = (Activity) context;
        this.packagesArrList = packagesArrList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_packages, null, true);

        ImageView ivPackagesImage = (ImageView) rowView.findViewById(R.id.iv_packages);
        TextView tvPackagesName = (TextView) rowView.findViewById(R.id.tv_package_name);

        System.out.println("Position: " + position);

        if(position == 0){
            ivPackagesImage.setImageResource(R.drawable.bluecard);
        }
        else if(position == 1){
            ivPackagesImage.setImageResource(R.drawable.orangecard);
        }
        else if(position == 2){
            ivPackagesImage.setImageResource(R.drawable.pioneercard);
        }
        tvPackagesName.setText(packagesArrList.get(position));
        return rowView;
    }
}
