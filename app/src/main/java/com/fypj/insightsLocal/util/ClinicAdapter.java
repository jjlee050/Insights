package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.model.Clinic;
import com.fypj.insightsLocal.model.Event;

import java.util.ArrayList;

/**
 * Created by L33524 on 22/9/2014.
 */
public class ClinicAdapter extends ArrayAdapter {
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
        TextView tvClinicAddress = (TextView) rowView.findViewById(R.id.tv_Clinic_Address);
        TextView tvClinicPostalCode = (TextView) rowView.findViewById(R.id.tv_Clinic_PostalCode);

        System.out.println("Position: " + position);

        tvClinicName.setText(ClinicArrList.get(position).getClinicName());
        tvClinicAddress.setText(ClinicArrList.get(position).getClinicAddress());
        tvClinicPostalCode.setText(ClinicArrList.get(position).getClinicPostalCode());
        return rowView;
    }
}
