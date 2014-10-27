package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.SubsidiesSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserPackagesSQLController;
import com.fypj.insightsLocal.sqlite_controller.UserSubsidiesSQLController;
import com.fypj.mymodule.api.insightsSubsidies.model.Subsidies;
import com.fypj.mymodule.api.insightsUser.model.User;
import com.fypj.mymodule.api.insightsUserPackages.model.UserPackages;
import com.fypj.mymodule.api.insightsUserSubsidies.model.UserSubsidies;

import java.text.DecimalFormat;
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
            UserPackagesSQLController userPackagesSQLController = new UserPackagesSQLController(context);
            SubsidiesSQLController  subsidiesSQLController = new SubsidiesSQLController(context);
            UserSubsidiesSQLController userSubsidiesSQLController = new UserSubsidiesSQLController(context);

            ArrayList<UserPackages> userPackagesArrList = userPackagesSQLController.getUserPackagesByNRIC(user.getNric());
            ArrayList<UserSubsidies> userSubsidiesArrList = userSubsidiesSQLController.getUserSubsidiesByNRIC(user.getNric());

            for(int i=0;i<userPackagesArrList.size();i++){
                Long packageID = userPackagesArrList.get(i).getPackagesID();
                ArrayList<Subsidies> subsidiesArrList = subsidiesSQLController.getSubsidiesByPackageID(packageID);
                for(int j=0;j<subsidiesArrList.size();j++) {
                    UserSubsidies userSubsidies = userSubsidiesSQLController.getUserSubsidies(user.getNric(),subsidiesArrList.get(i).getSubsidiesID());
                    DecimalFormat formatter = new DecimalFormat("$00.00");
                    tvInfo.setText(Html.fromHtml("<p>"+subsidiesArrList.get(i).getName()+": "+formatter.format(userSubsidies.getBalance())+"</p>"));
                }
            }

        }
        return rowView;
    }
}