package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.mymodule.api.insightsUser.model.User;

import java.util.ArrayList;

/**
 * Created by jess on 20-Sep-14.
 */
public class ProfileListAdapter extends ArrayAdapter<String> {
    private Activity context;
    private ArrayList<String> titleArrList;
    private User user;

    public ProfileListAdapter(Activity context, int textViewResourceId, ArrayList<String> titleArrList, User user) {
        super(context, R.layout.list_packages, titleArrList);
        this.context = (Activity) context;
        this.titleArrList = titleArrList;
        this.user = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_profile, null, true);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tvInfo = (TextView) rowView.findViewById(R.id.tv_info);
        System.out.println("Position: " + position);

        tvTitle.setText(titleArrList.get(position));
        if(position == 0){
            tvInfo.setText(Html.fromHtml("" +
                    "<p>Name: "+user.getName()+"</p>" +
                    "<p>Age: "+user.getAge()+"</p>" +
                    "<p>Contact No: "+user.getContactNo()+" </p>" +
                    "<p>Address: "+user.getAddress()+" </p>"));
        }
        else{
            tvInfo.setText(Html.fromHtml("" +
                    "<p>Simple Choronic conditions under CDMP: $66</p>" +
                    "<p>Complex Choronic conditions under CDMP: $100</p>" +
                    "<p>Medi-save: $200</p>"));
        }
        return rowView;
    }
}