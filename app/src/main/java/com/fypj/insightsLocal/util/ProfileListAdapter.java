package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        TableLayout tableLayoutInfo = (TableLayout) rowView.findViewById(R.id.tableLayout_info);
        System.out.println("Position: " + position);

        if(position == 0){
            tvTitle.setText(titleArrList.get(position));

            String[] leftValues = {"Name:", "Age:", "Contact No:", "Address:"};
            String[] rightValues = {user.getName(),String.valueOf(user.getAge()),user.getContactNo(),user.getAddress()};
                //tvLeft.setText(leftValues[i]);
                //tvRight.setText(rightValues[i]);
            for (int x = 0; x < leftValues.length; x++) {
                TextView tvLeft = new TextView(context);
                TextView tvRight = new TextView(context);
                tvLeft.setText(leftValues[x]);
                tvRight.setText(rightValues[x]);

                tvLeft.setTextSize(16);
                tvRight.setTextSize(16);
                TableRow tr = new TableRow(context);
                tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                tr.addView(tvLeft);
                tr.addView(tvRight);
                tvLeft.setPadding(16,16,32,16);
                tvRight.setPadding(32,16,16,16);
                tableLayoutInfo.addView(tr, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            //tableRowLeft.

        }
        else{
            UserPackagesSQLController userPackagesSQLController = new UserPackagesSQLController(context);
            SubsidiesSQLController  subsidiesSQLController = new SubsidiesSQLController(context);
            UserSubsidiesSQLController userSubsidiesSQLController = new UserSubsidiesSQLController(context);

            ArrayList<UserPackages> userPackagesArrList = userPackagesSQLController.getUserPackagesByNRIC(user.getNric());
            ArrayList<UserSubsidies> userSubsidiesArrList = userSubsidiesSQLController.getUserSubsidiesByNRIC(user.getNric());

            ArrayList<String> leftValuesArrList = new ArrayList<String>();
            ArrayList<Float> rightValuesArrList = new ArrayList<Float>();

            DecimalFormat formatter = new DecimalFormat("$00.00");

            for(int i=0;i<userPackagesArrList.size();i++){
                Long packageID = userPackagesArrList.get(i).getPackagesID();
                ArrayList<Subsidies> subsidiesArrList = subsidiesSQLController.getSubsidiesByPackageID(Long.parseLong(String.valueOf(position)));
                if(subsidiesArrList.size() > 0) {
                    tvTitle.setText(titleArrList.get(position));
                    for (int j = 0; j < subsidiesArrList.size(); j++) {
                        UserSubsidies userSubsidies = userSubsidiesSQLController.getUserSubsidies(user.getNric(), subsidiesArrList.get(j).getSubsidiesID());

                        if (userSubsidies.getSubsidiesID() != 0) {
                            leftValuesArrList.add(subsidiesArrList.get(j).getName());
                            rightValuesArrList.add(userSubsidies.getBalance());

                        }
                    }
                }
                else{
                    return null;
                }
            }

            /*ArrayList<String> duplicatesValuesArrList = new ArrayList<String>();

            Map<String, Integer> occurrencies = new HashMap<String, Integer>();
            for (String word : leftValuesArrList) {
                occurrencies.put(word, occurrencies.containsKey(word)
                        ? occurrencies.get(word) + 1 : 1);
            }
            int size = leftValuesArrList.size()-1;
            for (Map.Entry<String, Integer> entry : occurrencies.entrySet()) {
                System.out.println("Word: "+entry.getKey()
                        + ", occurences: "+entry.getValue());
                if(entry.getValue() > 1){
                    duplicatesValuesArrList.add(entry.getKey());
                }
            }

            ArrayList<Integer> getArrList = new ArrayList<Integer>();
            for(int i=0;i<duplicatesValuesArrList.size();i++){
                for(int j=0;j<leftValuesArrList.size();j++){
                    String duplicate = duplicatesValuesArrList.get(i);
                    if(duplicate.equals(leftValuesArrList.get(j))){
                        System.out.println("Position: " + j + ", " + leftValuesArrList.get(j));
                        getArrList.add(j);
                    }
                }
            }

            float balance = 0;
            int index = 0;
            for(int i=0;i<getArrList.size();i++){
                balance += rightValuesArrList.get(getArrList.get(i));
                if(balance - rightValuesArrList.get(getArrList.get(i)) != 0){
                    leftValuesArrList.remove(getArrList.get(i));
                    rightValuesArrList.remove(getArrList.get(i));
                }
                else{
                    index = getArrList.get(i);
                }
                System.out.println("Balance: " + balance);
            }
            rightValuesArrList.set(index,balance);*/


            for(int i=0;i<leftValuesArrList.size();i++){

                TextView tvLeft = new TextView(context);
                TextView tvRight = new TextView(context);
                tvLeft.setText(leftValuesArrList.get(i));
                tvRight.setText(formatter.format(rightValuesArrList.get(i)));


                tvLeft.setTextSize(16);
                tvRight.setTextSize(16);
                TableRow tr = new TableRow(context);
                tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                tr.addView(tvLeft);
                tr.addView(tvRight);
                tvLeft.setPadding(16, 16, 16, 16);
                tvRight.setPadding(16, 16, 16, 16);
                tableLayoutInfo.addView(tr, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
        return rowView;
    }
}