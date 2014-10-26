package com.fypj.insightsLocal.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fypj.insightsLocal.R;
import com.fypj.insightsLocal.sqlite_controller.UserSQLController;
import com.fypj.mymodule.api.insightsClinics.model.Clinic;
import com.fypj.mymodule.api.insightsPackages.model.Packages;
import com.fypj.mymodule.api.insightsUser.model.User;

import java.util.ArrayList;

/**
 * Created by jess on 26-Oct-14.
 */
public class CheckEligibilityListAdapter extends ArrayAdapter<Packages> {
    private Activity context;
    private ArrayList<Packages> packagesArrList;

    public CheckEligibilityListAdapter(Activity context, int textViewResourceId, ArrayList<Packages> packagesArrList) {
        super(context, R.layout.list_clinic, packagesArrList);
        this.context = (Activity) context;
        this.packagesArrList = packagesArrList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_eligibility, null, true);

        TextView tvEligible = (TextView) rowView.findViewById(R.id.tv_eligible);
        TextView tvPackageName = (TextView) rowView.findViewById(R.id.tv_package_name);
        ImageView ivImg = (ImageView) rowView.findViewById(R.id.iv_card_img);

        UserSQLController controller = new UserSQLController(context);
        SharedPreferences sharedPref = context.getSharedPreferences("insightsPreferences", Context.MODE_PRIVATE);
        String nric = sharedPref.getString("nric","");
        User user = controller.getUser(nric);
        int age = user.getAge();
        double houseHoldMonthlyIncome = user.getHouseHoldMonthlyIncome();
        double annualValueOfResidence = user.getAnnualValueOfResidence();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        int topParamsMargin = 340;
        int topParams1Margin = 130;
        int rightParams1Margin = 95;

        if(position == 0){
            params.setMargins(30, topParamsMargin, 0, 0);
            ivImg.setImageResource(R.drawable.pioneercard);
            if(age >= 65){
                tvEligible.setText("Eligible");
                tvEligible.setTextColor(context.getResources().getColor(R.color.greenDarkColor));
            }
            else{
                tvEligible.setText("Not Eligible");
                tvEligible.setTextColor(context.getResources().getColor(R.color.navigationalDrawerBackgroundColor));
            }
        }
        else if(position == 1){
            params.setMargins(175, topParamsMargin, 0, 0);
            ivImg.setImageResource(R.drawable.orangecard);
            if(((houseHoldMonthlyIncome > 1000) || (houseHoldMonthlyIncome <= 1800)) || ((annualValueOfResidence > 13000) || (annualValueOfResidence <= 21000))){
                tvEligible.setText("Eligible");
                tvEligible.setTextColor(context.getResources().getColor(R.color.greenDarkColor));
            }
            else {
                tvEligible.setText("Not Eligible");
                tvEligible.setTextColor(context.getResources().getColor(R.color.navigationalDrawerBackgroundColor));
            }

        }
        else if(position == 2){
            params.setMargins(200,topParamsMargin,0,0);
            ivImg.setImageResource(R.drawable.bluecard);
            if((houseHoldMonthlyIncome <= 1000) || (annualValueOfResidence <= 13000)){
                tvEligible.setText("Eligible");
                tvEligible.setTextColor(context.getResources().getColor(R.color.greenDarkColor));
            }
            else {
                tvEligible.setText("Not Eligible");
                tvEligible.setTextColor(context.getResources().getColor(R.color.navigationalDrawerBackgroundColor));
            }
        }

        if(tvEligible.getText().equals("Eligible")){
            params1.setMargins(0,topParams1Margin,rightParams1Margin,0);
            params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
                    tvEligible.getId());
            tvEligible.setLayoutParams(params1);
        }

        tvPackageName.setLayoutParams(params);
        tvPackageName.setText(packagesArrList.get(position).getName());
        return rowView;
    }
}
