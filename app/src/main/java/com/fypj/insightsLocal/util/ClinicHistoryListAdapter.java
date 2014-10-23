package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.mymodule.api.insightsMedicalHistory.model.MedicalHistory;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by L33525 on 26/9/2014.
 */
public class ClinicHistoryListAdapter extends ArrayAdapter<MedicalHistory> {
    private Activity context;
    private ArrayList<MedicalHistory> clinicHistoryArrayList;

    public ClinicHistoryListAdapter(Activity context, int textViewResourceId, ArrayList<MedicalHistory> clinicHistoryArrayList) {
        super(context, R.layout.list_clinic_history, clinicHistoryArrayList);
        this.context = context;
        this.clinicHistoryArrayList = clinicHistoryArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_clinic_history, null, true);
        TextView tvClinicName = (TextView) rowView.findViewById(R.id.tv_clinic_name);
        TextView tvService = (TextView) rowView.findViewById(R.id.tv_service);
        TextView tvAmt = (TextView) rowView.findViewById(R.id.tv_amt);

        DecimalFormat formatter = new DecimalFormat("$00.00");

        int clinicID = Integer.parseInt(String.valueOf(clinicHistoryArrayList.get(position).getClinicID()));
        tvClinicName.setText(String.valueOf(clinicID));
        tvService.setText(clinicHistoryArrayList.get(position).getService());
        tvAmt.setText(formatter.format(clinicHistoryArrayList.get(position).getAmt()));

        return rowView;
    }
}
